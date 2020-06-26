package com.example.ratelimiterdemo.generator;

import com.example.ratelimiterdemo.models.Developer;
import com.example.ratelimiterdemo.models.Organization;

import java.util.List;

public final class DummyDataGenerator {

    private DummyDataGenerator() {
    }

    public static Developer generateDummyDeveloperData(String developerId) {
        Developer developer = new Developer();
        developer.setId(developerId);
        developer.setName("A Developer");
        developer.setExpertise(List.of("Java", "Spring", "Maven"));
        return developer;
    }

    public static Organization generateDummyOrganizationData(String organizationId) {
        Organization organization = new Organization();
        organization.setId(organizationId);
        organization.setName("An Organization");
        return organization;
    }

}
