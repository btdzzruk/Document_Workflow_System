package com.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String user() {
        return "user ok";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "admin ok";
    }

    @GetMapping("/reviewer")
    @PreAuthorize("hasRole('REVIEWER')")
    public String reviewer() {
        return "reviewer ok";
    }

    @GetMapping("/approver")
    @PreAuthorize("hasRole('APPROVER')")
    public String approver() {
        return "approver ok";
    }
}
