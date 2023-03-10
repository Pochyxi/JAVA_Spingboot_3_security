package com.adiener.security3.auth;

import com.adiener.security3.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private LocalDate dateOfBirth;

    private Integer age;

    private Integer number;
    private Role role;
    private String token;
}
