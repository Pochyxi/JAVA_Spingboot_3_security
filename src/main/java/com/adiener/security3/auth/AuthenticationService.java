package com.adiener.security3.auth;

import com.adiener.security3.config.JwtService;
import com.adiener.security3.models.Role;
import com.adiener.security3.models.User;
import com.adiener.security3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register( RegisterRequest request ) {
        var user = User.builder()
                .firstName( request.getFirstname() )
                .lastName( request.getLastname() )
                .dateOfBirth( LocalDate.parse( request.getDateOfBirth() ) )
                .number( request.getNumber() )
                .email( request.getEmail())
                .password( passwordEncoder.encode( request.getPassword() ) )
                .role( Role.USER )
                .build();
        repository.save( user );
        var jwtToken =  jwtService.generateToken( user );
        return AuthenticationResponse.builder()
                .id( user.getId() )
                .firstName( user.getFirstName() )
                .lastName( user.getLastName() )
                .dateOfBirth( user.getDateOfBirth() )
                .age( user.calculateAge() )
                .number( user.getNumber() )
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
                .firstName( user.getFirstName() )
                .lastName( user.getLastName() )
                .dateOfBirth( user.getDateOfBirth() )
                .number( user.getNumber() )
                .age( user.calculateAge() )
                .email( user.getEmail() )
                .role( user.getRole() )
                .token( jwtToken )
                .build();
    }
}
