package edu.iu.c322.assetmanagement.licensingservice.client;

import edu.iu.c322.assetmanagement.licensingservice.model.Organization;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class OrganizationClient {
    private RestTemplate restTemplate;

    public OrganizationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "licensingService")
    public Optional<Organization> getOrganization(int organizationId){
        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        "http://organization-service/organizations/{organizationId}",
                        HttpMethod.GET,
                        null, Organization.class, organizationId);

        return Optional.ofNullable(restExchange.getBody());
    }
}
