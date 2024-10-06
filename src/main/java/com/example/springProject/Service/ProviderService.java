package com.example.springProject.Service;

import com.example.springProject.BO.AgentBO;
import com.example.springProject.BO.ProviderBO;
import com.example.springProject.DTO.ProviderDTO;
import com.example.springProject.Entity.Provider;
import com.example.springProject.Exception.ProviderManagementException;
import com.example.springProject.Exception.ProviderNotFoundException;
import com.example.springProject.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {

    @Autowired
    private ProviderBO providerBO;

    @Autowired
    private AgentBO agentBO;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private AgentRepository agentRepository;

    public ProviderDTO insert(ProviderDTO providerDTO) throws ProviderManagementException {
        return providerBO.insert(providerDTO);
    }

    public ProviderDTO findProvider(String providerId) throws ProviderManagementException, ProviderNotFoundException {
        Provider provider = providerBO.findProvider(providerId);
        if (provider == null) {
            throw new ProviderNotFoundException("Provider with id: " + providerId + " not found");
        }
        return ProviderDTO.toProviderDTO(provider);
    }

    public List<ProviderDTO> findAllProviders() throws ProviderManagementException {
        List<Provider> allProviders = providerBO.findAllProviders();
        return allProviders.stream()
                .map(ProviderDTO::toProviderDTO)
                .toList();
    }

    public List<Provider> deleteProvider(String providerId) throws ProviderNotFoundException {
        return providerBO.deleteProvider(providerId);
    }

    public List<ProviderProjectionInterface> findBySelectedColumn() throws ProviderManagementException {
        return providerBO.findBySelectedColumn();
    }

    public ProviderDTO updateProvider(String providerId, ProviderDTO providerDTO) throws ProviderManagementException, ProviderNotFoundException {
        return providerBO.updateProvider(providerId, providerDTO);
    }

    public Long countTotalProviders(){
        return providerBO.countTotalProviders();
    }

    public List<ProviderDTO> findProvidersWithMinimumAgents(){
        return providerBO.findProvidersWithMinimumAgents().stream()
                .map(ProviderDTO::toProviderDTO).toList();
    }

    public List<ProviderCountByStateProjectionInterface> countProvidersByState(){
        return providerBO.countProvidersByState();
    }
    public List<ProviderIdProjectionInterface> findProviderNamesAndEmailsOrderedByName(){
        return providerBO.findProviderNamesAndEmailsOrdered();
    }
    public List<ProviderDTO> findProvidersInnerJoinAgents(){
        return providerBO.findProvidersInnerJoinAgents().stream()
                .map(ProviderDTO::toProviderDTO)
                .toList();
    }
    public List<ProviderDTO> findAllProvidersLeftJoinAgents(){
        return providerBO.findAllProvidersLeftJoinAgents().stream()
                .map(ProviderDTO::toProviderDTO).toList();
    }
    public List<ProviderDTO> findAllProvidersRightJoinAgents(){
        return providerBO.findAllProvidersRightJoinAgents().stream()
                .map(ProviderDTO::toProviderDTO).toList();
    }
    public List<ProviderAgentProjectionInterface> findAllProvidersCrossJoinAgents(){
        return providerBO.findAllProvidersCrossJoinAgents();
    }
    public List<ProviderDTO> findAllByOrderByCreatedAtDesc(){
        return providerBO.findAllByOrderByCreatedAtDesc().stream()
                .map(ProviderDTO::toProviderDTO).toList();
    }
    public List<ProviderDTO> findAllWithAgents(){
        return providerBO.findAllWithAgents().stream()
                .map(ProviderDTO::toProviderDTO).toList();
    }
}
