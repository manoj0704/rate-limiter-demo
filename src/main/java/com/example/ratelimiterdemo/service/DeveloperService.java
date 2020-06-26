package com.example.ratelimiterdemo.service;

import com.example.ratelimiterdemo.models.Developer;
import org.springframework.stereotype.Service;

import static com.example.ratelimiterdemo.generator.DummyDataGenerator.generateDummyDeveloperData;

@Service
public class DeveloperService {

    public Developer fetchDeveloperDetails(String developerId) {
        return generateDummyDeveloperData(developerId);
    }
}
