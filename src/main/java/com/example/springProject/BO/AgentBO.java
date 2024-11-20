package com.example.springProject.BO;

import com.example.springProject.DTO.AgentDTO;
import com.example.springProject.Entity.Agents;
import com.example.springProject.Entity.Provider;
import com.example.springProject.Exception.AgentManagementException;
import com.example.springProject.Exception.AgentNotFoundException;
import com.example.springProject.Exception.ProviderManagementException;
import com.example.springProject.Repository.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class AgentBO {
    private static Logger log = Logger.getLogger(AgentBO.class);
    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private AgentStatusRepository agentStatusRepository;

    @Transactional
    public AgentDTO insert(AgentDTO agentDTO) throws AgentManagementException, ProviderManagementException {
        Agents agents = AgentDTO.toAgentEntity(agentDTO, providerRepository, agentRepository, cityRepository, stateRepository, agentStatusRepository);
        isValidInsert(agents);
        log.info("Inserting agent: " + agentDTO);
        if (agentRepository.findByAgentId(agents.getAgentId()) != null) {
            throw new AgentManagementException("Agent with id:" + agents.getAgentId() + " already exists");
        }
        log.info("Agent inserted successfully " + agents.getAgentId());
        return AgentDTO.toAgentDTO(agentRepository.save(agents));
    }

    public Agents findAgents(String agentsId) throws AgentManagementException, AgentNotFoundException {
        log.info("Finding agent with ID: " + agentsId);
        validateAgentId(agentsId);
        Agents agent = agentRepository.findByAgentId(agentsId);
        if (agent == null) {
            log.warn("Agent not found: " + agentsId);
            throw new AgentNotFoundException("Agent not found: " + agentsId);
        }
        log.info("Agent found: " + agent);
        return agent;
    }

    @Transactional
    public AgentDTO updateAgent(String agentId, AgentDTO agentDTO) throws AgentManagementException, AgentNotFoundException {
        Agents existingAgent = findAgents(agentId);
        if (existingAgent == null) {
            throw new AgentNotFoundException("Agent with ID " + agentId + " not found");
        }

        existingAgent.setFirstName(agentDTO.getFirstName());
        existingAgent.setLastName(agentDTO.getLastName());
        existingAgent.setEmail(agentDTO.getEmail());
        existingAgent.setStreet(agentDTO.getStreet());
        existingAgent.setCity(cityRepository.findById(agentDTO.getCityID())
                .orElseThrow(() -> new AgentManagementException("City not found")));
        existingAgent.setState(stateRepository.findById(agentDTO.getStateID())
                .orElseThrow(() -> new AgentManagementException("State not found")));
        existingAgent.setContact(agentDTO.getContact());
        existingAgent.setLicenseNumber(agentDTO.getLicenseNumber());
        existingAgent.setDateOfJoining(agentDTO.getDateOfJoining());
        existingAgent.setStatus(agentStatusRepository.findById(agentDTO.getStatus())
                .orElseThrow(() -> new AgentManagementException("Status not found")));
        if (agentDTO.getProviderId() != null) {
            Provider provider = providerRepository.findById(agentDTO.getProviderId())
                    .orElseThrow(() -> new AgentManagementException("Provider not found"));
            existingAgent.setProvider(provider);
        }

        isValidInsert(existingAgent);
        Agents updatedAgent = agentRepository.save(existingAgent);
        log.info("Agent updated successfully: " + updatedAgent.getAgentId());
        return AgentDTO.toAgentDTO(updatedAgent);
    }


    @Transactional
    public List<Agents> deleteAgent(String agentId) throws AgentNotFoundException, AgentManagementException {
        if (findAgents(agentId) == null) {
            throw new AgentNotFoundException("Agent with ID " + agentId + " not found");
        }
        log.info("Deleting agent with ID: " + agentId);
        Agents agents = agentRepository.findByAgentId(agentId);
        Provider provider = agents.getProvider();
        if (provider != null) {
            List<Agents> providerAgents = provider.getAgents();
            if (providerAgents.contains(agents)) {
                providerAgents.remove(agents);
                provider.setAgents(providerAgents);
                providerRepository.save(provider);
                log.info("Provider updated successfully after removing agent: " + agentId);
            }
        }
        agentRepository.deleteById(agentId);
        log.info("Provider deleted successfully: " + agentId);
        return findAllAgents();
    }


    public List<Agents> findAllAgents() {
        log.info("Fetching all agents");
        List<Agents> agents = agentRepository.findAll();
        log.info("Total agents found: " + agents.size());
        return agents;
    }

    public void isValidInsert(Agents agents) throws AgentManagementException {
        log.debug("Validating agent data for insertion: " + agents);
        if (!isValidEmail(agents.getEmail())) {
            log.error("Invalid email format: " + agents.getEmail());
            throw new AgentManagementException("Invalid email format: " + agents.getEmail());
        }
        if (agents.getAgentId() == null) {
            log.error("Agent ID cannot be null");
            throw new AgentManagementException("Agent ID cannot be null");
        }
        if (agents.getProvider() == null) {
            log.error("Agent cannot be null");
            throw new AgentManagementException("Agent cannot be null");
        }
        if (agents.getCity() == null) {
            log.error("City cannot be null");
            throw new AgentManagementException("City cannot be null");
        }
        if (agents.getState() == null) {
            log.error("State cannot be null");
            throw new AgentManagementException("State cannot be null");
        }
        if (agents.getStatus() == null) {
            log.error("Agent status cannot be null");
            throw new AgentManagementException("Agent status cannot be null");
        }
        if (agents.getProvider().getProviderId() == null) {
            log.error("Provider ID cannot be null");
            throw new AgentManagementException("Provider ID cannot be null");
        }
        if (agents.getLicenseNumber() == null) {
            log.error("License Number cannot be null");
            throw new AgentManagementException("License Number cannot be null");
        }
        if (agents.getDateOfJoining() == null) {
            log.error("Date of Joining cannot be null");
            throw new AgentManagementException("Date of Joining cannot be null");
        }
        if (agents.getFirstName() == null) {
            log.error("First Name cannot be null");
            throw new AgentManagementException("First Name cannot be null");
        }
        if (agents.getLastName() == null) {
            log.error("Last Name cannot be null");
            throw new AgentManagementException("Last Name cannot be null");
        }
        if (agents.getEmail() == null) {
            log.error("Email cannot be null");
            throw new AgentManagementException("Email cannot be null");
        }
        if (!agents.getFirstName().matches("[A-Za-z ]+")) {
            log.error("Agent Name should only contain letters, no digits allowed: " + agents.getFirstName());
            throw new AgentManagementException("Agent Name should only contain letters, no digits allowed: " + agents.getFirstName());
        }
        if (!agents.getLastName().matches("[A-Za-z ]+")) {
            log.error("Agent Name should only contain letters, no digits allowed: " + agents.getLastName());
            throw new AgentManagementException("Agent Name should only contain letters, no digits allowed: " + agents.getLastName());
        }
        if (!agents.getAgentId().matches("^[Aa][0-9]+$")) {
            log.error("Agent ID must start with 'A' or 'a' followed by digits: " + agents.getAgentId());
            throw new AgentManagementException("Agent ID must start with 'A' or 'a' followed by digits: " + agents.getAgentId());
        }
        if (agents.getContact() == null || agents.getContact().isEmpty()) {
            log.error("Agent contact number cannot be null or empty");
            throw new AgentManagementException("Agent contact number cannot be null or empty");
        }
        if (!agents.getContact().matches("[0-9]+")) {
            log.error("Contact number should only contain digits: " + agents.getContact());
            throw new AgentManagementException("Contact number should only contain digits: " + agents.getContact());
        }
        if (agents.getContact().length() != 10) {
            log.error("Agent contact number must be 10 digits: " + agents.getContact());
            throw new AgentManagementException("Agent contact number must be 10 digits: " + agents.getContact());
        }
        if (agents.getDateOfJoining().after(new Date())) {
            log.error("Date of Joining cannot be in future");
            throw new AgentManagementException("Date of Joining cannot be in future");
        }
        log.debug("Agent data validated successfully for insertion.");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

    @Transactional
    public List<Agents> deleteAgents(String agentId) throws AgentNotFoundException, AgentManagementException {
        if (findAgents(agentId) == null) {
            throw new AgentNotFoundException("Provider with ID " + agentId + " not found");
        }
        log.info("Deleting provider with ID: " + agentId);
        agentRepository.deleteById(agentId);
        log.info("Provider deleted successfully: " + agentId);
        return findAllAgents();
    }

    private void validateAgentId(String agentId) throws AgentManagementException {
        if (agentId.isEmpty()) {
            log.error("Agent ID cannot be empty");
            throw new AgentManagementException("Agent ID cannot be empty");
        }
        if (!agentId.matches("^[Aa][0-9]+$")) {
            log.error("Agent ID must start with 'A' or 'a' followed by digits: " + agentId);
            throw new AgentManagementException("Agent ID must start with 'A' or 'a' followed by digits: " + agentId);
        }
        log.debug("Agent ID {} validated successfully." + agentId);
    }
}
