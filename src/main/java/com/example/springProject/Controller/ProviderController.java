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
//        String userRole = (String) request.getSession().getAttribute("role");
//        if (!"ADMIN".equals(userRole)) {
//            return ResponseHandler.getResponse("Access denied. Only admins can create providers.", HttpStatus.FORBIDDEN, null);
//        }
        log.info("Creating provider with details: " + providerDTO);
        List<ProviderDTO> allProviders = providerService.findAllProviders();
        log.info("List of all providers before insertion: " + allProviders);
        ProviderDTO createdProvider = providerService.insert(providerDTO);
        List<ProviderDTO> allProvidersAfter = providerService.findAllProviders();
        log.info("Provider created successfully: " + createdProvider);
        log.info("List of providers after insertion: " + allProvidersAfter);
        return ResponseHandler.getResponse("Provider created successfully", HttpStatus.CREATED, createdProvider);
    }

    @GetMapping("/user/home")
    public String userHome() {
        return "Since you have logged in as user redirecting to user home";
    }

    @GetMapping("{providerId}")
    public ResponseEntity<Object> getProvider(@PathVariable String providerId, HttpServletRequest request) throws ProviderNotFoundException, ProviderManagementException {
//        String userRole = (String) request.getSession().getAttribute("role");
//        if (!"ADMIN".equals(userRole)) {
//            return ResponseHandler.getResponse("Access denied. Only admins can create providers.", HttpStatus.FORBIDDEN, null);
//        }
        log.debug("Fetching provider by ID: " + providerId);
        ProviderDTO providerDTO = providerService.findProvider(providerId);
        log.info("Provider found: " + providerDTO);
        return ResponseHandler.getResponse("Provider found", HttpStatus.OK, providerDTO);
    }

    @GetMapping
    public ResponseEntity<Object> getAllProviders() throws ProviderManagementException {
        log.debug("Fetching all providers");
        List<ProviderDTO> providers = providerService.findAllProviders();
        log.info("Total providers retrieved: " + providers.size() + " Details of the providers fetched: " + providers);
        return ResponseHandler.getResponse("Providers retrieved successfully", HttpStatus.OK, providers);
    }

    @DeleteMapping("/DeleteProvider/{providerId}")
    public ResponseEntity<Object> deleteProvider(@PathVariable String providerId) throws ProviderNotFoundException, ProviderManagementException {
        log.debug("Deleting provider by ID: " + providerId);
        List<ProviderDTO> providers = providerService.findAllProviders();
        log.debug("All providers before deletion: " + providers);
        List<Provider> deletedProvider = providerService.deleteProvider(providerId);
        List<ProviderDTO> providersAfter = providerService.findAllProviders();
        log.info("Provider deleted successfully: " + deletedProvider);
        log.info("All providers after deletion: " + providersAfter);
        return ResponseHandler.getResponse("Provider deleted successfully", HttpStatus.OK, deletedProvider);
    }

    @PutMapping("/update/{providerId}")
    public ResponseEntity<Object> updateProvider(@PathVariable String providerId, @RequestBody ProviderDTO providerDTO) throws ProviderManagementException, ProviderNotFoundException {
        log.info("Updating provider with id " + providerId);
        ProviderDTO updatedProvider = providerService.updateProvider(providerId, providerDTO);
        log.info("Provider updated successfully: " + updatedProvider);
        return ResponseHandler.getResponse("Provider updated successfully", HttpStatus.OK, updatedProvider);
    }


    @GetMapping("/ProjectionInterface")
    public ResponseEntity<Object> findBySelectedColumn() throws ProviderManagementException {
        log.debug("Fetching specific columns from providers");
        List<ProviderProjectionInterface> findByColumns = providerService.findBySelectedColumn();
        log.info("Specific columns retrieved successfully: " + findByColumns);
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
        log.info("Providers with minimum agents found: " + providersWithMinimumAgents.size());
        return ResponseHandler.getResponse("Providers with minimum agents", HttpStatus.OK, providersWithMinimumAgents);
    }

    @GetMapping("/countProviderByState")
    public ResponseEntity<Object> countProvidersByState() {
        log.debug("Counting providers by state");
        List<ProviderCountByStateProjectionInterface> countByState = providerService.countProvidersByState();
        log.info("Count of providers by state retrieved: " + countByState);
        return ResponseHandler.getResponse("Count of providers by state", HttpStatus.OK, countByState);
    }

    @GetMapping("/ProviderByOrder")
    public ResponseEntity<Object> findProviderNamesAndEmailsOrdered() {
        log.debug("Fetching provider names and emails ordered by name");
        List<ProviderIdProjectionInterface> orderedJpqlVariable = providerService.findProviderNamesAndEmailsOrderedByName();
        log.info("Providers ordered by name: " + orderedJpqlVariable);
        return ResponseHandler.getResponse("Providers ordered by name", HttpStatus.OK, orderedJpqlVariable);
    }

    @GetMapping("/innerJoin")
    public ResponseEntity<Object> findProvidersWithAgents() {
        log.debug("Fetching providers with inner join on agents");
        List<ProviderDTO> innerJoinAgents = providerService.findProvidersInnerJoinAgents();
        log.info("Providers with inner join found: " + innerJoinAgents.size() + innerJoinAgents);
        return ResponseHandler.getResponse("Providers inner join agents", HttpStatus.OK, innerJoinAgents);
    }

    @GetMapping("/leftJoin")
    public ResponseEntity<Object> findAllProvidersLeftJoinAgents() {
        log.debug("Fetching all providers with left join on agents");
        List<ProviderDTO> leftJoinAgents = providerService.findAllProvidersLeftJoinAgents();
        log.info("Providers with left join found: " + leftJoinAgents.size() + leftJoinAgents);
        return ResponseHandler.getResponse("Providers left join agents", HttpStatus.OK, leftJoinAgents);
    }

    @GetMapping("/rightJoin")
    public ResponseEntity<Object> findAllProvidersRightJoinAgents() {
        log.debug("Fetching all providers with right join on agents");
        List<ProviderDTO> rightJoinAgents = providerService.findAllProvidersRightJoinAgents();
        log.info("Providers with right join found: " + rightJoinAgents.size() + rightJoinAgents);
        return ResponseHandler.getResponse("Providers Right join agents", HttpStatus.OK, rightJoinAgents);
    }

    @GetMapping("/crossJoin")
    public ResponseEntity<Object> findAllProvidersCrossJoinAgents() {
        log.debug("Fetching all providers with cross join on agents");
        List<ProviderAgentProjectionInterface> crossJoinAgents = providerService.findAllProvidersCrossJoinAgents();
        log.info("Providers with cross join found: " + crossJoinAgents.size() + crossJoinAgents);
        return ResponseHandler.getResponse("Providers Cross join agents", HttpStatus.OK, crossJoinAgents);
    }

    @GetMapping("/namedQuery1")
    public ResponseEntity<Object> findAllByOrderByCreatedAtDesc() {
        log.debug("Named query");
        List<ProviderDTO> named = providerService.findAllByOrderByCreatedAtDesc();
        log.info("Named Query 1: " + named);
        return ResponseHandler.getResponse("Named Query 1", HttpStatus.OK, named);
    }

    @GetMapping("/namedQuery2")
    public ResponseEntity<Object> findAllWithAgents() {
        log.debug("Fetching all providers with agents from named query");
        List<ProviderDTO> allWithAgents = providerService.findAllWithAgents();
        log.info("Providers with agents found: " + allWithAgents.size() + allWithAgents);
        return ResponseHandler.getResponse("Providers with agents", HttpStatus.OK, allWithAgents);
    }
}
