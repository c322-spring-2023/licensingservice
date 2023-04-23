package edu.iu.c322.assetmanagement.licensingservice.controller;

import edu.iu.c322.assetmanagement.licensingservice.client.OrganizationClient;
import edu.iu.c322.assetmanagement.licensingservice.model.License;
import edu.iu.c322.assetmanagement.licensingservice.model.Organization;
import edu.iu.c322.assetmanagement.licensingservice.repository.LicenseRepository;
import edu.iu.c322.assetmanagement.licensingservice.service.LicensingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/licensings")
public class LicensingController {
    private LicensingService licensingService;



    public LicensingController(LicensingService licensingService) {
        this.licensingService = licensingService;
    }

    @GetMapping
    public List<License> getLicensings() throws TimeoutException {
        return licensingService.getLicensings();
    }

    @GetMapping("/{id}")
    public License getLicensing(@PathVariable int id){
      return licensingService.getLicensing(id);
    }



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public int create(@RequestBody License license){
        return licensingService.create(license);
    }

}
