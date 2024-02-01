package io.github.AndCandido.apispringsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpController {

    @GetMapping("/public")
    public String publicRoute() {
        return "This is the Public Route";
    }

    @GetMapping("/private")
    public String privateRoute() {
        return "This is the Private Route ";
    }
}
