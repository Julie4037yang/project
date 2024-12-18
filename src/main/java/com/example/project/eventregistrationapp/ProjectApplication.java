package com.example.project.eventregistrationapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// 標註這是Spring Boot應用程序的入口類
@SpringBootApplication(scanBasePackages = {"com.example.project.controller", "com.example.project.repository", "com.example.project.service"})

// 啟用JPA repositories，指定repository所在的package
@EnableJpaRepositories(basePackages = "com.example.project.repository")

// 指定Entity類所在的package
@EntityScan(basePackages = "com.example.project.entity")

public class ProjectApplication {
    public static void main(String[] args) {
        // 啟動Spring Boot應用程序
        SpringApplication.run(ProjectApplication.class, args);
    }
}

