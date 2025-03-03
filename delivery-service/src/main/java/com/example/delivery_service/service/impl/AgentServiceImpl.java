package com.example.delivery_service.service.impl;

import com.example.delivery_service.dto.AgentDto;
import com.example.delivery_service.model.Address;
import com.example.delivery_service.model.Agent;
import com.example.delivery_service.repository.AgentRepository;
import com.example.delivery_service.service.AgentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.UUID;
@Service
public class AgentServiceImpl implements AgentService {

    private final ModelMapper modelMapper;
    private final AgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AgentServiceImpl(ModelMapper modelMapper,
                            AgentRepository agentRepository,
                           PasswordEncoder passwordEncoder){
        this.modelMapper = modelMapper;
        this.agentRepository = agentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AgentDto createAgent(AgentDto agentDto) {
        try{
            Agent agent = modelMapper.map(agentDto, Agent.class);

            if (agentRepository.findByUsername(agentDto.getUsername()) != null) {
                throw new InvalidParameterException("Username '" + agentDto.getUsername() + "' is already taken.");
            }

            if (agentDto.getAddress() != null) {
                Address address = modelMapper.map(agentDto.getAddress(), Address.class);
                address.setAgent(agent);
                agent.setAddress(address);
            }

            agent.setPassword(passwordEncoder.encode(agentDto.getPassword()));
            Agent savedAgent = agentRepository.save(agent);
            savedAgent.setPassword("");
            return modelMapper.map(savedAgent, AgentDto.class);
        } catch (Exception e) {
            throw new InvalidParameterException("Passed information is not valid | Correct the credentials");
        }
    }


    @Override
    public AgentDto getAgentById(UUID id) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        AgentDto agentDto = modelMapper.map(agent, AgentDto.class);
        agentDto.setPassword("");
        return agentDto;
    }

    @Override
    public Page<AgentDto> getAllAgent(Pageable pageable) {
        return agentRepository.findAll(pageable).
                map(agent -> modelMapper.map(agent, AgentDto.class));
    }

    @Override
    public AgentDto updateAgent(UUID customerId, AgentDto updatedAgent) {
        Agent agent = agentRepository.findById(customerId).orElseThrow(
                () -> new RuntimeException("Cannot find customer")
        );
        Agent updatedAgentEntity = modelMapper.map(updatedAgent, Agent.class);
        agent.setAddress(updatedAgentEntity.getAddress());
        agent.setFirstName(updatedAgentEntity.getFirstName());
        agent.setLastName(updatedAgentEntity.getLastName());
        agent.setUsername(updatedAgentEntity.getUsername());

        Agent savedAgent =  agentRepository.save(agent);
        return modelMapper.map(savedAgent, AgentDto.class);
    }

    @Override
    public void deleteAgent(UUID agentId) {
        Agent agents = agentRepository.findById(agentId).
                orElseThrow(() -> new RuntimeException("Cannot find customer"));
        agentRepository.deleteById(agentId);
    }

    @Override
    public AgentDto getAgentByEmail(String email) {
        Agent agent = agentRepository.findByUsername(email);
        if(agent == null){
            throw new RuntimeException("Cannot find username in UserServiceImp");
        }
        return modelMapper.map(agent, AgentDto.class);
    }

}
