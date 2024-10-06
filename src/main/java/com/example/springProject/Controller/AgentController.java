package com.example.springProject.Controller;

import com.example.springProject.DTO.AgentDTO;
import com.example.springProject.Entity.Agents;
import com.example.springProject.Exception.AgentManagementException;
import com.example.springProject.Exception.AgentNotFoundException;
import com.example.springProject.Exception.ProviderManagementException;
import com.example.springProject.Response.ResponseHandler;
import com.example.springProject.Service.AgentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agent")
public class AgentController {
    static Logger log = Logger.getLogger(AgentController.class);

    @Autowired
    private AgentService agentService;

    @PostMapping("/create")
    public ResponseEntity<Object> insert(@RequestBody AgentDTO agentDTO) throws AgentManagementException, ProviderManagementException {
        List<AgentDTO> listOfAgentBefore = agentService.findAllAgents();
        log.info("Total agents before creation: " + listOfAgentBefore.size() + " Details of the agents fetched: " + listOfAgentBefore);
        log.info("Creating agent with details: " + agentDTO);
        AgentDTO createdAgent = agentService.insert(agentDTO);
        List<AgentDTO> listOfAgentAfter = agentService.findAllAgents();
        log.info("Total agents after creation: " + listOfAgentAfter.size() + " Details of the agents fetched: " + listOfAgentAfter);
        log.info("Agent created successfully: " + createdAgent);
        return ResponseHandler.getResponse("Agent created successfully", HttpStatus.CREATED, createdAgent);
    }

    @GetMapping("{agentId}")
    public ResponseEntity<Object> getAgent(@PathVariable String agentId) throws AgentManagementException, AgentNotFoundException {
        log.debug("Fetching agent by ID: " + agentId);
        AgentDTO agentDTO = agentService.findAgent(agentId);
        log.info("Agent found: " + agentDTO);
        return ResponseHandler.getResponse("Agent found", HttpStatus.OK, agentDTO);
    }

    @PutMapping("/update/{agentId}")
    public ResponseEntity<Object> updateProvider(@PathVariable String agentId, @RequestBody AgentDTO agentDTO) throws AgentManagementException, AgentNotFoundException {
        log.info("Updating Agent with id " + agentId);
        AgentDTO updatedAgent = agentService.updateAgent(agentId, agentDTO);
        log.info("Agent updated successfully: " + updatedAgent);
        return ResponseHandler.getResponse("Agent updated successfully", HttpStatus.OK, updatedAgent);
    }

    @DeleteMapping("/deleteAgent/{agentId}")
    public ResponseEntity<Object> deleteAgent(@PathVariable String agentId) throws AgentNotFoundException, AgentManagementException {
        log.debug("Deleting agent by ID: " + agentId);
        List<AgentDTO> agents = agentService.findAllAgents();
        log.debug("All agents before deletion: " + agents);
        List<Agents> deletedAgent = agentService.deleteAgent(agentId);
        List<AgentDTO> agentsAfter = agentService.findAllAgents();
        log.info("Agents deleted successfully: " + deletedAgent);
        log.info("All agents after deletion: " + agentsAfter);
        return ResponseHandler.getResponse("Provider deleted successfully", HttpStatus.OK, deletedAgent);
    }

    @GetMapping
    public ResponseEntity<Object> getAllAgents() throws AgentManagementException {
        log.debug("Fetching all agents");
        List<AgentDTO> agents = agentService.findAllAgents();
        log.info("Total agents retrieved: " + agents.size() + " Details of the agents fetched: " + agents);
        return ResponseHandler.getResponse("Agents retrieved successfully", HttpStatus.OK, agents);
    }
}
