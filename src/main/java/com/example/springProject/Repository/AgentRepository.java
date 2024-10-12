package com.example.springProject.Repository;


import com.example.springProject.Entity.Agents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agents,String > {
    Agents findByAgentId(String id);
    boolean existsByAgentId(String agentId);
}
