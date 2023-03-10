package com.adiener.security3.responseModels;

import com.adiener.security3.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;

    private Integer age;

    private Integer number;
    private Role role;
}
