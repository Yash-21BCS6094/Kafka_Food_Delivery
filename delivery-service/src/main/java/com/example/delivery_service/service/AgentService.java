package com.example.delivery_service.service;

import com.example.delivery_service.dto.AgentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface AgentService {
    AgentDto createAgent(AgentDto agentDto);
    AgentDto getAgentById(UUID id);
    Page<AgentDto> getAllAgent(Pageable pageable);
    AgentDto updateAgent(UUID customerId, AgentDto updatedCustomer);
    void deleteAgent(UUID customerId);
    AgentDto getAgentByEmail(String email);
}
