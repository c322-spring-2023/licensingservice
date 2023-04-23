package edu.iu.c322.assetmanagement.licensingservice.service;

import edu.iu.c322.assetmanagement.licensingservice.client.OrganizationClient;
import edu.iu.c322.assetmanagement.licensingservice.model.License;
import edu.iu.c322.assetmanagement.licensingservice.model.Organization;
import edu.iu.c322.assetmanagement.licensingservice.repository.LicenseRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeoutException;

@Service
public class LicensingService {
    private LicenseRepository licenseRepository;

    private OrganizationClient organizationClient;

    private static final Logger logger = LoggerFactory.getLogger(LicensingService.class);

    public LicensingService(LicenseRepository repository, OrganizationClient organizationClient) {
        this.licenseRepository = repository;
        this.organizationClient = organizationClient;
    }


    public List<License> getLicensings() throws TimeoutException {
        randomlyRunLong();
        return licenseRepository.findAll();
    }

    public License getLicensing(int id){

        Optional<License> maybeLicense = licenseRepository.findById(id);
        if(maybeLicense.isPresent()){
            License license = maybeLicense.get();
            Optional<Organization> maybeOrganization = organizationClient
                    .getOrganization(license.getOrganizationId());
            if(maybeOrganization.isPresent()){
                Organization organization = maybeOrganization.get();
                license.setOrganization(organization);
                return license;
            }
        } else {
            throw new IllegalStateException("licensing id is invalid.");
        }
        return null;
    }

    public int create(License license){
        License addedLicense = licenseRepository.save(license);
        return addedLicense.getId();
    }

    private void randomlyRunLong() throws TimeoutException {
        Random rand = new Random();
        int randomNum = rand.nextInt(2);
        if (randomNum==0) sleep();
    }
    private void sleep() throws TimeoutException{
        try {
            System.out.println("Sleep");
            Thread.sleep(5000);
            throw new java.util.concurrent.TimeoutException();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

}
