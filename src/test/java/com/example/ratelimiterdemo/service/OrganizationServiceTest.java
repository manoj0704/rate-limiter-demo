package com.example.ratelimiterdemo.service;

import com.example.ratelimiterdemo.models.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrganizationServiceTest {

    private OrganizationService organizationService;

    @BeforeEach
    void setUp() {
        organizationService = new OrganizationService();
    }

    @Test
    public void shouldFetchOrganizationDetails() {
        String organizationId = "12345";
        Organization organization = organizationService.fetchOrganizationDetails(organizationId);
        assertThat(organization.getId()).isEqualTo(organizationId);
        assertThat(organization.getName()).isEqualTo("An Organization");
    }

}