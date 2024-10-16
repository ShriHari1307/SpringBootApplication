package com.example.springProject.Repository;

import com.example.springProject.Entity.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderTypeRepository extends JpaRepository<ProviderType, Integer> {
    ProviderType findByType(String providerType);
}
