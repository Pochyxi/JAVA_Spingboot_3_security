package com.adiener.security3.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller/")
public class DemoController {

    @GetMapping("users/{id}")
    @PreAuthorize("hasRole('ADMIN') or (#id == authentication.principal.id)")
    public ResponseEntity<String> sayHello(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return ResponseEntity.ok( "Ciaoo dall'endpoint sicuro :)" + id);
    }
}
