package com.example;

import com.example.config.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.example")
@EnableScheduling // job ktra quá hạn
public class DocumentWorkflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentWorkflowApplication.class, args);
    }

}
