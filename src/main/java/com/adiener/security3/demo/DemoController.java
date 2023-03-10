package com.adiener.security3.demo;

import com.adiener.security3.models.User;
import com.adiener.security3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/demo-controller/")
@RequiredArgsConstructor
public class DemoController {

    private final UserRepository userRepository;

    @GetMapping("users/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> sayHello(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object name = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        System.out.println(name);
        System.out.println(authorities);

        return ResponseEntity.ok( "Saluti dalla tua API preferita ;)");
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> updateUser( @PathVariable("id") Integer id, @RequestBody User userToUpdate) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            // prima di salvare controlliamo che l'utente della richiesta è lo stesso utente della modifica
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();

            if (userEmail.equals(userToUpdate.getUsername())) {

                User updatedUser = userRepository.save(userToUpdate);
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.status( HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
