package com.adiener.security3.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    private String firstName;
    private String lastName;
    @Column(unique = true, length = 16)
    private String username;
    private String password;

    private LocalDate dateOfBirth;

    private Integer number;

    @Transient
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Integer calculateAge() {
        if (this.dateOfBirth != null) {
            LocalDate now = LocalDate.now();
            Period period = Period.between(this.dateOfBirth, now);
            this.age = period.getYears();
        }
        return this.age;
    }

    public void setDateOfBirthDay( LocalDate dateOfBirthDay ) {
        this.dateOfBirth = dateOfBirthDay;
        this.age = null; // reset age when dateOfBirthDay is updated
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority( role.name() ) );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
