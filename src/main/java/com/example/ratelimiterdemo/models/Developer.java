package com.example.ratelimiterdemo.models;

import lombok.Data;

import java.util.List;

@Data
public class Developer {

    private String id;

    private String name;

    private List<String> expertise;

}
