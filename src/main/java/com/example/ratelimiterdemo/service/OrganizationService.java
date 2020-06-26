package com.example.ratelimiterdemo.service;

import com.example.ratelimiterdemo.models.Organization;
import org.springframework.stereotype.Service;

import static com.example.ratelimiterdemo.generator.DummyDataGenerator.generateDummyOrganizationData;

@Service
public class OrganizationService {
    public Organization fetchOrganizationDetails(String organizationId) {
        return generateDummyOrganizationData(organizationId);
    }
}
