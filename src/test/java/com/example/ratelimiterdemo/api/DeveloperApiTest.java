package com.example.ratelimiterdemo.api;

import com.example.ratelimiterdemo.models.Developer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static com.example.ratelimiterdemo.generator.DummyDataGenerator.generateDummyDeveloperData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class DeveloperApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldFetchDeveloperDetails() throws Exception {
        String developerId = "123456";
        Developer expectedDeveloper = generateDummyDeveloperData(developerId);

        String urlBuilder = "http://localhost:" + port + "/api/v1/developers/{developerId}";

        ResponseEntity<Developer> response = restTemplate.getForEntity(urlBuilder,
                Developer.class, developerId);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualToComparingFieldByField(expectedDeveloper);
    }
}