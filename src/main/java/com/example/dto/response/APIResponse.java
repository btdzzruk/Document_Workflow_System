package com.example.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class APIResponse <T>{
    private boolean success;
    private String message;
    private T data;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
