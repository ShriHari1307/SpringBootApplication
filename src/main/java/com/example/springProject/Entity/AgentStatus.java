package com.example.springProject.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Entity
@Scope("prototype")
@Table(name = "agent_status")
public class AgentStatus {

    @Id
    @Column(name = "status_id", unique = true, nullable = false)
    private Integer statusId;

    @Column(name = "status")
    private String status;
}
