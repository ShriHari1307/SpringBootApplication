package com.example.springProject.Repository;

import com.example.springProject.Entity.AgentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentStatusRepository extends JpaRepository<AgentStatus, Integer> {
}
