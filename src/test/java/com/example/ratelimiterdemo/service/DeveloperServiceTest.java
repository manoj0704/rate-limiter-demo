package com.example.ratelimiterdemo.service;

import com.example.ratelimiterdemo.models.Developer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DeveloperServiceTest {

    private DeveloperService developerService;

    @BeforeEach
    void setUp() {
        developerService = new DeveloperService();
    }

    @Test
    public void shouldFetchDeveloperDetails() {
        String developerId = "123456";
        Developer developer = developerService.fetchDeveloperDetails(developerId);

        assertThat(developer.getId()).isEqualTo(developerId);
        assertThat(developer.getName()).isEqualTo("A Developer");
        assertThat(developer.getExpertise()).containsOnly("Java", "Spring", "Maven");
    }
}