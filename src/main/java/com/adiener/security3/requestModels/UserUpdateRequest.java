package com.adiener.security3.requestModels;

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
public class UserUpdateRequest {
    private Integer id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Integer number;

    private Role role;
}
