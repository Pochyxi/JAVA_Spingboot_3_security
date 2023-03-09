package com.adiener.security3.auth;

import com.adiener.security3.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String token;
}
