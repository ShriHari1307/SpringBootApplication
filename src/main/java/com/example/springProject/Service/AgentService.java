package com.example.springProject.Service;

import com.example.springProject.BO.AgentBO;
import com.example.springProject.DTO.AgentDTO;
import com.example.springProject.Entity.Agents;
import com.example.springProject.Exception.AgentManagementException;
import com.example.springProject.Exception.AgentNotFoundException;
import com.example.springProject.Exception.ProviderManagementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentService {

    @Autowired
    private AgentBO agentBO;

    public AgentDTO insert(AgentDTO agentDTO) throws ProviderManagementException, AgentManagementException {
        return agentBO.insert(agentDTO);
    }

    public AgentDTO findAgent(String agentId) throws AgentManagementException, AgentNotFoundException {
        Agents agents = agentBO.findAgents(agentId);
        if (agents == null) {
            throw new AgentNotFoundException("Agent with id: " + agentId + " not found");
        }
        return AgentDTO.toAgentDTO(agents);
    }

    public List<AgentDTO> findAllAgents() {
        List<Agents> allAgents = agentBO.findAllAgents();
        return allAgents.stream().map(AgentDTO::toAgentDTO).toList();
    }

    public AgentDTO updateAgent(String agentId, AgentDTO agentDTO) throws AgentNotFoundException, AgentManagementException {
        return agentBO.updateAgent(agentId, agentDTO);
    }

    public List<Agents> deleteAgent(String agentId) throws AgentNotFoundException, AgentManagementException {
        return agentBO.deleteAgent(agentId);
    }

}
