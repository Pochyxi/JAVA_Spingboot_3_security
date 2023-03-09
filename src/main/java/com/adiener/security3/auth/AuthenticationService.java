package com.adiener.security3.auth;

import com.adiener.security3.config.JwtService;
import com.adiener.security3.user.Role;
import com.adiener.security3.user.User;
import com.adiener.security3.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register( RegisterRequest request ) {
        var user = User.builder()
                .firstname( request.getFirstname() )
                .lastname( request.getLastname() )
                .email( request.getEmail())
                .password( passwordEncoder.encode( request.getPassword() ) )
                .role( Role.USER )
                .build();
        repository.save( user );
        var jwtToken =  jwtService.generateToken( user );
        return AuthenticationResponse.builder()
                .id( user.getId() )
                .firstName( user.getFirstname() )
                .lastName( user.getLastname() )
                .email( user.getEmail() )
                .role( user.getRole() )
                .token( jwtToken )
                .build();
    }

    public AuthenticationResponse authenticate( AuthenticationRequest request ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail( request.getEmail() )
                .orElseThrow();
        var jwtToken =  jwtService.generateToken( user );
        return AuthenticationResponse.builder()
                .id( user.getId() )
                .firstName( user.getFirstname() )
                .lastName( user.getLastname() )
                .email( user.getEmail() )
                .role( user.getRole() )
                .token( jwtToken )
                .build();
    }
}
