package com.example.ratelimiterdemo;

import com.example.ratelimiterdemo.models.Developer;
import com.example.ratelimiterdemo.models.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class RateLimiterDemoApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String developerApi;

    private String organizationApi;

    private String user1 = "user1";

    private String user2 = "user2";

    @BeforeEach
    void setUp() {
        developerApi = "http://localhost:" + port + "/api/v1/developers/{developerId}";
        organizationApi = "http://localhost:" + port + "/api/v1/organizations/{organizationId}";
    }

    @Test
    public void testRateLimitingForDeveloperApiForUser1() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("user", "user1");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        List<HttpStatus> responseList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ResponseEntity<Developer> responseEntity = restTemplate.exchange(developerApi, GET, entity,
                    Developer.class, "12345");
            responseList.add(responseEntity.getStatusCode());
        }
        assertThat(responseList).containsExactly(OK, OK, TOO_MANY_REQUESTS);
    }

    @Test
    public void testRateLimitingForDeveloperApiForUser2() throws InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("user", "user2");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        List<HttpStatus> responseList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            ResponseEntity<Developer> responseEntity = restTemplate.exchange(developerApi, GET, entity,
                    Developer.class, "12345");
            responseList.add(responseEntity.getStatusCode());
        }
        assertThat(responseList).containsExactly(OK, OK, OK, OK, OK, TOO_MANY_REQUESTS);

        //Wait for 30 seconds and try
        TimeUnit.SECONDS.sleep(30);
        ResponseEntity<Developer> responseEntity = restTemplate.exchange(developerApi, GET, entity,
                Developer.class, "12345");
        assertThat(responseEntity.getStatusCode()).isEqualTo(TOO_MANY_REQUESTS);

        //Wait for 30 seconds again and try
        TimeUnit.SECONDS.sleep(30);
        responseEntity = restTemplate.exchange(developerApi, GET, entity,
                Developer.class, "12345");
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    }

    @Test
    public void testRateLimitingForOrganizationApiForUser1() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("user", "user2");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        List<HttpStatus> responseList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            ResponseEntity<Organization> responseEntity = restTemplate.exchange(organizationApi, GET, entity,
                    Organization.class, "12345");
            responseList.add(responseEntity.getStatusCode());
        }
        assertThat(responseList).containsExactly(OK, OK, OK, TOO_MANY_REQUESTS);
    }

    @Test
    public void testDefaultRateLimitingForOrganizationApiForUser1() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("user", "user1");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        List<HttpStatus> responseList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            ResponseEntity<Organization> responseEntity = restTemplate.exchange(organizationApi, GET, entity,
                    Organization.class, "12345");
            responseList.add(responseEntity.getStatusCode());
        }
        assertThat(responseList).containsExactly(OK, OK, OK, OK, OK, TOO_MANY_REQUESTS);
    }

    @Test
    public void testDefaultRateLimitingForDeveloperApiWithoutUser() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        List<HttpStatus> responseList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            ResponseEntity<Developer> responseEntity = restTemplate.exchange(developerApi, GET, entity,
                    Developer.class, "12345");
            responseList.add(responseEntity.getStatusCode());
        }
        assertThat(responseList).containsExactly(OK, OK, OK, OK, OK, TOO_MANY_REQUESTS);
    }
}
