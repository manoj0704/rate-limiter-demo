package com.example.ratelimiterdemo.api;

import com.example.ratelimiterdemo.models.Developer;
import com.example.ratelimiterdemo.ratelimiter.EnableRateLimit;
import com.example.ratelimiterdemo.service.DeveloperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1")
@Slf4j
public class DeveloperApi {

    private final DeveloperService developerService;

    public DeveloperApi(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping("/developers/{developerId}")
    @EnableRateLimit(api = "getDeveloperDetails")
    public Developer fetchDeveloperDetails(@PathVariable String developerId) {
        return developerService.fetchDeveloperDetails(developerId);
    }
}
