package com.example.ratelimiterdemo.api;

import com.example.ratelimiterdemo.models.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static com.example.ratelimiterdemo.generator.DummyDataGenerator.generateDummyOrganizationData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class OrganizationApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldFetchDeveloperDetails() throws Exception {
        String organizationId = "123456";
        Organization expectedOrganization = generateDummyOrganizationData(organizationId);

        String urlBuilder = "http://localhost:" + port + "/api/v1/organizations/{organizationId}";

        ResponseEntity<Organization> response = restTemplate.getForEntity(urlBuilder,
                Organization.class, organizationId);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualToComparingFieldByField(expectedOrganization);
    }
}