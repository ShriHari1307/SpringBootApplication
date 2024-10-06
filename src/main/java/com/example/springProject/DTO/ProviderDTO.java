package com.example.springProject.DTO;

import com.example.springProject.Entity.Agents;
import com.example.springProject.Entity.Provider;
import com.example.springProject.Exception.ProviderManagementException;
import com.example.springProject.Repository.AgentRepository;
import com.example.springProject.Repository.CityRepository;
import com.example.springProject.Repository.ProviderTypeRepository;
import com.example.springProject.Repository.StateRepository;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProviderDTO {
    private String providerId;
    private String providerName;
    private String contactNumber;
    private String email;
    private String providerType;
    private String street;
    private Integer cityId;
    private Integer stateId;
    private List<String> agentIds;
    public static  ProviderDTO toProviderDTO(Provider provider) {
        return ProviderDTO.builder()
                .providerName(provider.getProviderName())
                .providerId(provider.getProviderId())
                .providerType(provider.getProviderType().getType())
                .email(provider.getEmail())
                .contactNumber(provider.getContactNumber())
                .street(provider.getStreet())
                .cityId(provider.getCity().getCityId())
                .stateId(provider.getState().getStateId())
                .agentIds(provider.getAgents().stream().map(Agents::getAgentId)
                        .toList()).build();
    }

    public static Provider toProviderEntity(ProviderDTO providerDTO, CityRepository cityRepository, StateRepository stateRepository, ProviderTypeRepository providerTypeRepository, AgentRepository agentRepository) throws ProviderManagementException {
        List<Agents> agents = providerDTO.getAgentIds().stream().map(agentRepository::findByAgentId).toList();
        return Provider.builder()
                .providerId(providerDTO.getProviderId())
                .providerName(providerDTO.getProviderName())
                .contactNumber(providerDTO.getContactNumber())
                .email(providerDTO.getEmail())
                .street(providerDTO.getStreet())
                .providerType(providerTypeRepository.findByType(providerDTO.getProviderType()))
                .city(cityRepository.findById(providerDTO.getCityId()).orElseThrow(() -> new ProviderManagementException("City not found")))
                .state(stateRepository.findById(providerDTO.getStateId()).orElseThrow(() -> new ProviderManagementException("State not found")))
                .agents(agents)
                .build();
    }
}
