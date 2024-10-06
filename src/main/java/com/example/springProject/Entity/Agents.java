package com.example.springProject.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@Entity
@Scope("prototype")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agents {

    @Id
    @Column(name = "agent_id")
    private String agentId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;

    @Column(name = "date_of_joining")
    @Temporal(TemporalType.DATE)
    private Date dateOfJoining;

    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "provider_id")
    @JsonBackReference
    private Provider provider;

    @Column(name = "street")
    private String street;

    @ManyToOne
    @JoinColumn(name = "city", referencedColumnName = "cityID")
    private City city;

    @ManyToOne
    @JoinColumn(name = "state", referencedColumnName = "stateID")
    private State state;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "status", referencedColumnName = "status_id")
    private AgentStatus status;

    @Override
    public String toString() {
        return "Agents{" +
                "agentId='" + agentId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contact='" + contact + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", dateOfJoining=" + dateOfJoining +
                ", providerId=" + (provider != null ? provider.getProviderId() : "null") +
                ", cityId=" + (city != null ? city.getCityId() : "null") +
                ", stateId=" + (state != null ? state.getStateId() : "null") +
                ", email='" + email + '\'' +
                ", statusId=" + (status != null ? status.getStatusId() : "null") +
                '}';
    }
}
