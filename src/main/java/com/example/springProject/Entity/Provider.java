package com.example.springProject.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Data
@Component
@Entity
@Builder
@NamedQuery(name = "findAllByOrderByCreatedAtDesc", query = "SELECT p FROM Provider p ORDER BY p.createdAt DESC")
@NamedQuery(name = "joinNamedQuery",query = "SELECT p FROM Provider p JOIN p.agents a")

@Table(name = "providers")
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
    @Id
    @Column(name = "provider_id")
    @NotNull
    private String providerId;
    @Column(name = "provider_name", nullable = false)
    private String providerName;

    @ManyToOne
    @JoinColumn(name = "provider_type", referencedColumnName = "type_id", nullable = false)
    private ProviderType providerType;

    @Column(name = "contact_number", unique = true, nullable = false)
    private String contactNumber;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "street", nullable = false)
    private String street;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "city", referencedColumnName = "cityID")
    private City city;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "state", referencedColumnName = "stateID")
    private State state;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date updatedAt;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "provider")
    @JsonManagedReference
    private List<Agents> agents;
}
