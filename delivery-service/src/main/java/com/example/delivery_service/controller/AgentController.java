package com.example.delivery_service.controller;

import com.example.delivery_service.dto.AgentDto;
import com.example.delivery_service.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("api/v1/agent")
public class AgentController {

    private final AgentService agentService;

    @Autowired
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping
    public ResponseEntity<AgentDto> createAgent(@RequestBody AgentDto agentDto) {
        return new ResponseEntity<>(agentService.createAgent(agentDto), HttpStatus.CREATED);
    }

    @PutMapping("/{agentId}")
    public ResponseEntity<AgentDto> updateAgent(@PathVariable UUID agentId, @RequestBody AgentDto agentDto) {
        return new ResponseEntity<>(agentService.updateAgent(agentId, agentDto), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{agentId}")
    public ResponseEntity<AgentDto> partialUpdateAgent(@PathVariable UUID agentId, @RequestBody AgentDto agentDto) {
        return new ResponseEntity<>(agentService.updateAgent(agentId, agentDto), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<?> getAgents(@RequestParam(required = false) String agentId,
                                      @RequestParam(required = false) String email,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) Integer page,
                                      @RequestParam(required = false) Integer size) {
        if (agentId != null) {
            UUID uid = UUID.fromString(agentId);
            return new ResponseEntity<>(agentService.getAgentById(uid), HttpStatus.OK);
        } else if (email != null) {
            return new ResponseEntity<>(agentService.getAgentByEmail(email), HttpStatus.OK);
        } else if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size);
            return new ResponseEntity<>(agentService.getAllAgent(pageable), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid request parameters", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{agentId}")
    public ResponseEntity<String> deleteAgents(@PathVariable UUID agentId) {
        agentService.deleteAgent(agentId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.NO_CONTENT);
    }

}
