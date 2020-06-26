package com.example.ratelimiterdemo.api;

import com.example.ratelimiterdemo.models.Organization;
import com.example.ratelimiterdemo.ratelimiter.EnableRateLimit;
import com.example.ratelimiterdemo.service.OrganizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OrganizationApi {

    private final OrganizationService organizationService;

    public OrganizationApi(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/organizations/{organizationId}")
    @EnableRateLimit(api = "getOrganizationDetails")
    public Organization fetchOrganizationDetails(@PathVariable String organizationId) {
        return organizationService.fetchOrganizationDetails(organizationId);
    }
}
