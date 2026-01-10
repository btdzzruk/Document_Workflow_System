package com.example;

import com.example.config.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class DocumentWorkflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentWorkflowApplication.class, args);
    }

}
