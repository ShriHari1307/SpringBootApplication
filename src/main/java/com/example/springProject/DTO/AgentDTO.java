package com.example.springProject.DTO;

import com.example.springProject.Entity.Agents;
import com.example.springProject.Exception.ProviderManagementException;
import com.example.springProject.Repository.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AgentDTO {
    private String agentId;
    private String firstName;
    private String lastName;
    private String contact;
    private String licenseNumber;
    private Date dateOfJoining;
    private String providerId;
    private String street;
    private String email;
    private Integer cityID;
    private Integer stateID;
    private Integer status;

    public static AgentDTO toAgentDTO(Agents agents){
        return AgentDTO.builder()
                .agentId(agents.getAgentId())
                .firstName(agents.getFirstName())
                .lastName(agents.getLastName())
                .contact(agents.getContact())
                .licenseNumber(agents.getLicenseNumber())
                .dateOfJoining(agents.getDateOfJoining())
                .providerId(agents.getProvider().getProviderId())
                .street(agents.getStreet())
                .cityID(agents.getCity().getCityId())
                .stateID(agents.getState().getStateId())
                .email(agents.getEmail())
                .status(agents.getStatus().getStatusId())
                .build();
    }


    public static Agents toAgentEntity(AgentDTO agentDTO, ProviderRepository providerRepository, AgentRepository agentRepository, CityRepository cityRepository, StateRepository stateRepository, AgentStatusRepository agentStatusRepository) throws ProviderManagementException {
        return Agents.builder()
               .agentId(agentDTO.getAgentId())
               .firstName(agentDTO.getFirstName())
               .lastName(agentDTO.getLastName())
               .contact(agentDTO.getContact())
                .city(cityRepository.findById(agentDTO.getCityID()).orElseThrow(() -> new ProviderManagementException("City not found")))
               .state(stateRepository.findById(agentDTO.getStateID()).orElseThrow(() -> new ProviderManagementException("State not found")))
               .licenseNumber(agentDTO.getLicenseNumber())
               .dateOfJoining(agentDTO.getDateOfJoining())
               .provider(providerRepository.findById(agentDTO.getProviderId()).orElseThrow(() -> new ProviderManagementException("Provider not found")))
               .street(agentDTO.getStreet())
               .email(agentDTO.getEmail())
               .status(agentStatusRepository.findById(agentDTO.getStatus()).orElseThrow(() -> new ProviderManagementException("Status not found")))
               .build();
    }
}
