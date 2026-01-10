package com.example.test;

import io.minio.MinioClient;
import io.minio.BucketExistsArgs;

public class MinioConnectionTest {
    public static void main(String[] args) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://103.118.29.245:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();

            // Test kết nối
            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket("dung")
                            .build()
            );

            System.out.println("Connection successful! Bucket exists: " + exists);
        } catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}