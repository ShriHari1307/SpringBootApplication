package com.example.springProject.BO;

import com.example.springProject.DTO.ProviderDTO;
import com.example.springProject.Entity.Agents;
import com.example.springProject.Entity.Provider;
import com.example.springProject.Exception.ProviderManagementException;
import com.example.springProject.Exception.ProviderNotFoundException;
import com.example.springProject.Repository.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class ProviderBO {

    private static Logger log = Logger.getLogger(ProviderBO.class);

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private ProviderRepository repo;

    @Autowired
    private ProviderTypeRepository providerTypeRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Transactional
    public ProviderDTO insert(ProviderDTO providerDTO) throws ProviderManagementException {
        Provider provider = ProviderDTO.toProviderEntity(providerDTO, cityRepository, stateRepository, providerTypeRepository, agentRepository);
        isValidInsert(provider);
        if (repo.findByProviderId(provider.getProviderId()).isPresent()) {
            throw new ProviderManagementException("Provider already exists");
        }
        log.info("Provider inserted successfully: " + provider.getProviderId());
        return ProviderDTO.toProviderDTO(repo.save(provider));
    }

    @Transactional(readOnly = true)
    public Provider findProvider(String providerId) throws ProviderManagementException, ProviderNotFoundException {
        validateProviderId(providerId);
        Provider provider = repo.findById(providerId).orElse(null);
        if (provider == null) {
            log.warn("Provider not found: " + providerId);
            throw new ProviderNotFoundException("Provider not found: " + providerId);
        }
        log.info("Provider found: ");
        return provider;
    }

    @Transactional(readOnly = true)
    public List<Provider> findAllProviders() {
        return repo.findAll();
    }

    @Transactional
    public List<Provider> deleteProvider(String providerId) throws ProviderNotFoundException {
        if (repo.findByProviderId(providerId).isEmpty()) {
            throw new ProviderNotFoundException("Provider with ID " + providerId + " not found");
        }
        log.info("Deleting provider with ID: " + providerId);
        repo.deleteById(providerId);
        log.info("Provider deleted successfully: " + providerId);
        return findAllProviders();
    }

    @Transactional
    public ProviderDTO updateProvider(String providerId, ProviderDTO providerDTO) throws ProviderManagementException, ProviderNotFoundException {
        Provider existingProvider = findProvider(providerId);
        if (existingProvider == null) {
            throw new ProviderNotFoundException("Provider with ID " + providerId + " not found");
        }
        existingProvider.setProviderName(providerDTO.getProviderName());
        existingProvider.setEmail(providerDTO.getEmail());
        existingProvider.setStreet(providerDTO.getStreet());
        existingProvider.setCity(cityRepository.findById(providerDTO.getCityId()).orElseThrow(() -> new ProviderManagementException("City not found")));
        existingProvider.setState(stateRepository.findById(providerDTO.getStateId()).orElseThrow(() -> new ProviderManagementException("State not found")));
        existingProvider.setProviderType(providerTypeRepository.findByType(providerDTO.getProviderType()));
        existingProvider.setContactNumber(providerDTO.getContactNumber());
        List<Agents> agents = agentRepository.findAllById(providerDTO.getAgentIds());
        existingProvider.setAgents(agents);
        isValidInsert(existingProvider);
        Provider updatedProvider = repo.save(existingProvider);
        log.info("Provider updated successfully: " + updatedProvider.getProviderId());
        return ProviderDTO.toProviderDTO(updatedProvider);
    }

    private void isValidInsert(Provider provider) throws ProviderManagementException {
        log.debug("Validating provider data for insertion: " + provider);

        if (provider.getProviderId() == null) {
            log.error("Provider ID cannot be null");
            throw new ProviderManagementException("Provider ID cannot be null");
        }

        if (provider.getProviderName() == null) {
            log.error("Provider Name cannot be null");
            throw new ProviderManagementException("Provider Name cannot be null");
        }

        if (provider.getEmail() == null) {
            log.error("Provider Email cannot be null");
            throw new ProviderManagementException("Provider email cannot be null");
        }

        if (provider.getProviderType() == null) {
            log.error("Provider Type cannot be null");
            throw new ProviderManagementException("Provider type cannot be null");
        }

        if (provider.getStreet() == null) {
            log.error("Provider Street cannot be null");
            throw new ProviderManagementException("Provider street cannot be null");
        }

        if (provider.getCity() == null) {
            log.error("Provider City cannot be null");
            throw new ProviderManagementException("Provider city cannot be null");
        }

        if (provider.getState() == null) {
            log.error("Provider State cannot be null");
            throw new ProviderManagementException("Provider state cannot be null");
        }

        if (!provider.getProviderName().matches("[A-Za-z ]+")) {
            log.error("Provider Name should only contain letters, no digits allowed: " + provider.getProviderName());
            throw new ProviderManagementException("Provider Name should only contain letters, no digits allowed: " + provider.getProviderName());
        }

        if (!provider.getProviderId().matches("^[Pp][0-9]+$")) {
            log.error("Provider ID must start with 'P' or 'p' followed by digits: " + provider.getProviderId());
            throw new ProviderManagementException("Provider ID must start with 'P' or 'p' followed by digits: " + provider.getProviderId());
        }

        if (provider.getContactNumber() == null || provider.getContactNumber().isEmpty()) {
            log.error("Provider contact number cannot be null or empty");
            throw new ProviderManagementException("Provider contact number cannot be null or empty");
        }

        if (!provider.getContactNumber().matches("[0-9]+")) {
            log.error("Contact number should only contain digits: " + provider.getContactNumber());
            throw new ProviderManagementException("Contact number should only contain digits: " + provider.getContactNumber());
        }

        if (provider.getContactNumber().length() != 10) {
            log.error("Provider contact number must be 10 digits: " + provider.getContactNumber());
            throw new ProviderManagementException("Provider contact number must be 10 digits: " + provider.getContactNumber());
        }

        if (!isValidEmail(provider.getEmail())) {
            log.error("Invalid email format: " + provider.getEmail());
            throw new ProviderManagementException("Invalid email format: " + provider.getEmail());
        }

        log.debug("Provider data validated successfully for insertion.");
    }

    private boolean isValidEmail(String email) {
        log.debug("Validating provider email: " + email);
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

    private void validateProviderId(String providerId) throws ProviderManagementException {
        log.debug("Validating provider ID: " + providerId);
        if (providerId.isEmpty()) {
            log.error("Provider ID cannot be empty");
            throw new ProviderManagementException("Provider ID cannot be empty");
        }

        if (!providerId.matches("^[Pp][0-9]+$")) {
            log.error("Provider ID must start with 'P' or 'p' followed by digits: " + providerId);
            throw new ProviderManagementException("Provider ID must start with 'P' or 'p' followed by digits: " + providerId);
        }

        log.debug("Provider ID {} validated successfully. " + providerId);
    }

    public Long countTotalProviders() {
        return repo.countTotalProviders();
    }

    public List<Provider> findProvidersWithMinimumAgents() {
        return repo.findProvidersWithMinimumAgents();
    }

    public List<ProviderCountByStateProjectionInterface> countProvidersByState() {
        return repo.countProvidersByState();
    }

    public List<ProviderIdProjectionInterface> findProviderNamesAndEmailsOrdered() {
        return repo.findProviderNamesAndEmailsOrdered();
    }

    public List<Provider> findProvidersInnerJoinAgents() {
        return repo.findProvidersInnerJoinAgents();
    }

    public List<Provider> findAllProvidersLeftJoinAgents() {
        return repo.findAllProvidersLeftJoinAgents();
    }

    public List<Provider> findAllProvidersRightJoinAgents() {
        return repo.findAllProvidersRightJoinAgents();
    }

    public List<ProviderAgentProjectionInterface> findAllProvidersCrossJoinAgents() {
        return repo.findAllProvidersCrossJoinAgents();
    }

    public List<ProviderProjectionInterface> findBySelectedColumn() throws ProviderManagementException {
        return repo.findBySelectedColumn();
    }

    public List<Provider> findAllByOrderByCreatedAtDesc() {
        return repo.findAllByOrderByCreatedAtDesc();
    }

    public List<Provider> findAllWithAgents() {
        return repo.findAllWithAgents();
    }
}
