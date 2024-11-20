package com.example.springProject.Repository;

import com.example.springProject.Entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, String> {

    @Query("select p.providerId as providerId, p.providerName as providerName, p.email as email from Provider p")
    List<ProviderProjectionInterface> findBySelectedColumn();

    Optional<Provider> findByProviderId(String providerId);


    @Query("select count(p) from Provider p")
    Long countTotalProviders();

    @Query("select p from Provider p where size(p.agents) = (select min(size(p2.agents)) from Provider p2)")
    List<Provider> findProvidersWithMinimumAgents();

    @Query("SELECT p.state.stateName AS stateName, COUNT(p) AS providerCount FROM Provider p GROUP BY p.state.stateName")
    List<ProviderCountByStateProjectionInterface> countProvidersByState();

    @Query("SELECT p.providerName AS providerName, p.email AS email FROM Provider p ORDER BY p.providerName ASC")
    List<ProviderIdProjectionInterface> findProviderNamesAndEmailsOrdered();

    @Query("SELECT p FROM Provider p JOIN p.agents a")
    List<Provider> findProvidersInnerJoinAgents();

    @Query("SELECT p FROM Provider p LEFT JOIN p.agents a")
    List<Provider> findAllProvidersLeftJoinAgents();

    @Query("SELECT p FROM Provider p RIGHT JOIN p.agents a")
    List<Provider> findAllProvidersRightJoinAgents();

    @Query("SELECT p.providerName AS providerName, p.email AS providerEmail, a.firstName AS agentFirstName, a.lastName AS agentLastName, a.email AS agentEmail FROM Provider p, Agents a")
    List<ProviderAgentProjectionInterface> findAllProvidersCrossJoinAgents();

    @Query(name = "findAllByOrderByCreatedAtDesc")
    List<Provider> findAllByOrderByCreatedAtDesc();

    @Query(name = "joinNamedQuery")
    List<Provider> findAllWithAgents();


}
