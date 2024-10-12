package com.example.springProject.Controller;

import com.example.springProject.DTO.ProviderDTO;
import com.example.springProject.Entity.Provider;
import com.example.springProject.Exception.ProviderManagementException;
import com.example.springProject.Exception.ProviderNotFoundException;
import com.example.springProject.Repository.ProviderAgentProjectionInterface;
import com.example.springProject.Repository.ProviderCountByStateProjectionInterface;
import com.example.springProject.Repository.ProviderIdProjectionInterface;
import com.example.springProject.Repository.ProviderProjectionInterface;
import com.example.springProject.Response.ResponseHandler;
import com.example.springProject.Service.ProviderService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provider")
public class ProviderController {
    static Logger log = Logger.getLogger(ProviderController.class);

    @Autowired
    private ProviderService providerService;

    @PostMapping("/create")
    public ResponseEntity<Object> insert(@RequestBody ProviderDTO providerDTO, HttpServletRequest request) throws ProviderManagementException {
        log.info("Insert method called");
        log.info("Creating provider with details: " + providerDTO);
        List<ProviderDTO> allProviders = providerService.findAllProviders();
        for (ProviderDTO allProvider : allProviders) {
            log.info("List of all providers before insertion: " + allProvider);
        }
        ProviderDTO createdProvider = providerService.insert(providerDTO);
        List<ProviderDTO> allProvidersAfter = providerService.findAllProviders();
        log.info("Provider created successfully: " + createdProvider);
        for (ProviderDTO dto : allProvidersAfter) {
            log.info("List of providers after insertion: " + dto);
        }
        return ResponseHandler.getResponse("Provider created successfully", HttpStatus.CREATED, createdProvider);
    }

    @GetMapping("{providerId}")
    public ResponseEntity<Object> getProvider(@PathVariable String providerId, HttpServletRequest request) throws ProviderNotFoundException, ProviderManagementException {
        log.info("Fetch by id method called");
        log.debug("Fetching provider by ID: " + providerId);
        ProviderDTO providerDTO = providerService.findProvider(providerId);
        log.info("Provider found: " + providerDTO);
        return ResponseHandler.getResponse("Provider found", HttpStatus.OK, providerDTO);
    }

//    @GetMapping("/token")
//    public CsrfToken getCsrfToken(HttpServletRequest request) {
//        return (CsrfToken) request.getAttribute("_csrf");
//    }

    @GetMapping
    public ResponseEntity<Object> getAllProviders() throws ProviderManagementException {
        log.info("Fetch all method called");
        log.debug("Fetching all providers");
        List<ProviderDTO> providers = providerService.findAllProviders();
        log.info("Total providers retrieved: " + providers.size());
        for (ProviderDTO provider : providers) {
            log.info(" Details of the providers fetched: " + provider);
        }
        return ResponseHandler.getResponse("Providers retrieved successfully", HttpStatus.OK, providers);
    }

    @DeleteMapping("/DeleteProvider/{providerId}")
    public ResponseEntity<Object> deleteProvider(@PathVariable String providerId) throws ProviderNotFoundException, ProviderManagementException {
        log.info("Delete function called");
        log.debug("Deleting provider by ID: " + providerId);
        List<ProviderDTO> providers = providerService.findAllProviders();
        log.info("Total providers retrieved before deletion: " + providers.size());
        for (ProviderDTO provider : providers) {
            log.debug("Details of providers before deletion: " + providers);
        }
        List<Provider> deletedProvider = providerService.deleteProvider(providerId);
        List<ProviderDTO> providersAfter = providerService.findAllProviders();
        log.info("Provider deleted successfully: " + deletedProvider);
        log.info("Total providers retrieved after deletion: " + providersAfter.size());
        for (ProviderDTO providerDTO : providersAfter) {
            log.info("Details of providers after deletion: " + providersAfter);
        }
        return ResponseHandler.getResponse("Provider deleted successfully", HttpStatus.OK, deletedProvider);
    }

    @PutMapping("/update/{providerId}")
    public ResponseEntity<Object> updateProvider(@PathVariable String providerId, @RequestBody ProviderDTO providerDTO) throws ProviderManagementException, ProviderNotFoundException {
        log.info("Update function called");
        log.info("Updating provider with id " + providerId);
        ProviderDTO updatedProvider = providerService.updateProvider(providerId, providerDTO);
        log.info("Provider updated successfully: " + updatedProvider);
        return ResponseHandler.getResponse("Provider updated successfully", HttpStatus.OK, updatedProvider);
    }


    @GetMapping("/ProjectionInterface")
    public ResponseEntity<Object> findBySelectedColumn() throws ProviderManagementException {
        log.debug("Fetching specific columns from providers");
        List<ProviderProjectionInterface> findByColumns = providerService.findBySelectedColumn();
        for (ProviderProjectionInterface findByColumn : findByColumns) {
            log.info("Specific columns retrieved successfully: " + findByColumn);
        }
        return ResponseHandler.getResponse("Specific columns retrieved successfully", HttpStatus.OK, findByColumns);
    }


    @GetMapping("/countProvider")
    public ResponseEntity<Object> countTotalProviders() {
        log.debug("Counting total providers");
        Long totalProviders = providerService.countTotalProviders();
        log.info("Total number of providers: " + totalProviders);
        return ResponseHandler.getResponse("Total number of providers", HttpStatus.OK, totalProviders);
    }

    @GetMapping("/providersWithMinimumAgents")
    public ResponseEntity<Object> findProvidersWithMinimumAgents() {
        log.debug("Finding providers with minimum agents");
        List<ProviderDTO> providersWithMinimumAgents = providerService.findProvidersWithMinimumAgents();
        for (ProviderDTO providersWithMinimumAgent : providersWithMinimumAgents) {
            log.info("Providers with minimum agents found: " + providersWithMinimumAgents.size() + "Details: " + providersWithMinimumAgent);
        }
        return ResponseHandler.getResponse("Providers with minimum agents", HttpStatus.OK, providersWithMinimumAgents);
    }

    @GetMapping("/countProviderByState")
    public ResponseEntity<Object> countProvidersByState() {
        log.debug("Counting providers by state");
        List<ProviderCountByStateProjectionInterface> countByState = providerService.countProvidersByState();
        for (ProviderCountByStateProjectionInterface providerCount : countByState) {
            log.info("Provider count by state: " + providerCount);
        }
        return ResponseHandler.getResponse("Count of providers by state", HttpStatus.OK, countByState);
    }

    @GetMapping("/ProviderByOrder")
    public ResponseEntity<Object> findProviderNamesAndEmailsOrdered() {
        log.debug("Fetching provider names and emails ordered by name");
        List<ProviderIdProjectionInterface> orderedJpqlVariable = providerService.findProviderNamesAndEmailsOrderedByName();
        for (ProviderIdProjectionInterface providerIdProjectionInterface : orderedJpqlVariable) {
            log.info("Providers ordered by name: " + providerIdProjectionInterface);
        }
        return ResponseHandler.getResponse("Providers ordered by name", HttpStatus.OK, orderedJpqlVariable);
    }

    @GetMapping("/innerJoin")
    public ResponseEntity<Object> findProvidersWithAgents() {
        log.debug("Fetching providers with inner join on agents");
        List<ProviderDTO> innerJoinAgents = providerService.findProvidersInnerJoinAgents();
        for (ProviderDTO innerJoinAgent : innerJoinAgents) {
            log.info("Providers with inner join found: " + innerJoinAgents.size() + innerJoinAgent);
        }
        return ResponseHandler.getResponse("Providers inner join agents", HttpStatus.OK, innerJoinAgents);
    }

    @GetMapping("/leftJoin")
    public ResponseEntity<Object> findAllProvidersLeftJoinAgents() {
        log.debug("Fetching all providers with left join on agents");
        List<ProviderDTO> leftJoinAgents = providerService.findAllProvidersLeftJoinAgents();
        log.info("Providers with left join found: " + leftJoinAgents.size());
        for (ProviderDTO leftJoinAgent : leftJoinAgents) {
            log.info("Details of Providers left join agents: " + leftJoinAgent);
        }
        return ResponseHandler.getResponse("Providers left join agents", HttpStatus.OK, leftJoinAgents);
    }

    @GetMapping("/rightJoin")
    public ResponseEntity<Object> findAllProvidersRightJoinAgents() {
        log.debug("Fetching all providers with right join on agents");
        List<ProviderDTO> rightJoinAgents = providerService.findAllProvidersRightJoinAgents();
        log.info("Providers with right join found: " + rightJoinAgents.size());
        for (ProviderDTO rightJoinAgent : rightJoinAgents) {
            log.info("Providers Right join agents : " + rightJoinAgent);
        }
        return ResponseHandler.getResponse("Providers Right join agents", HttpStatus.OK, rightJoinAgents);
    }

    @GetMapping("/crossJoin")
    public ResponseEntity<Object> findAllProvidersCrossJoinAgents() {
        log.debug("Fetching all providers with cross join on agents");
        List<ProviderAgentProjectionInterface> crossJoinAgents = providerService.findAllProvidersCrossJoinAgents();
        log.info("Providers with cross join found: " + crossJoinAgents.size());
        for (ProviderAgentProjectionInterface crossJoinAgent : crossJoinAgents) {
            log.info("Providers Cross join agents : " + crossJoinAgent);
        }
        return ResponseHandler.getResponse("Providers Cross join agents", HttpStatus.OK, crossJoinAgents);
    }

    @GetMapping("/namedQuery1")
    public ResponseEntity<Object> findAllByOrderByCreatedAtDesc() {
        log.debug("Named query");
        List<ProviderDTO> named = providerService.findAllByOrderByCreatedAtDesc();
        for (ProviderDTO providerDTO : named) {
            log.info("Named Query 1: " + providerDTO);
        }
        return ResponseHandler.getResponse("Named Query 1", HttpStatus.OK, named);
    }

    @GetMapping("/namedQuery2")
    public ResponseEntity<Object> findAllWithAgents() {
        log.debug("Fetching all providers with agents from named query");
        List<ProviderDTO> allWithAgents = providerService.findAllWithAgents();
        log.info("Providers with agents found: " + allWithAgents.size());
        for (ProviderDTO allWithAgent : allWithAgents) {
            log.info("Providers with agents from named query : " + allWithAgent);
        }

        return ResponseHandler.getResponse("Providers with agents", HttpStatus.OK, allWithAgents);
    }
}
