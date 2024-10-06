package com.example.springProject.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Entity
@Scope("prototype")
@Table(name = "providertype")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderType {
    @Id
    @Column(name = "type_id")
    private Integer typeId;

    @Column(name = "type")
    private String type;
}
