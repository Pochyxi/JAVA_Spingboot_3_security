package com.adiener.security3.services;

import com.adiener.security3.models.Role;
import com.adiener.security3.models.User;
import com.adiener.security3.repository.UserRepository;
import com.adiener.security3.requestModels.UserUpdateRequest;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // salvataggio utente
    public User save (User user) {
        return this.userRepository.save( user );
    }

    // modifica utente esistente
    public User update(User user) {
        return userRepository.save( user );
    }

    public User creatAndUpdateUserAdmin( UserUpdateRequest request) throws Exception {
        User userF = userRepository.findById( request.getId() )
                .orElseThrow(() -> new Exception( "User not found"));
        User userN = User.builder()
                // default
                .id( userF.getId() )
                .username( userF.getUsername() )
                .role( userF.getRole() )
                .password( userF.getPassword() )
                .age( userF.calculateAge() )
                // modified
                .firstName( request.getFirstName() == null ? userF.getFirstName() : request.getFirstName() )
                .lastName( request.getLastName() == null ? userF.getLastName() : request.getLastName() )
                .dateOfBirth( request.getDateOfBirth() == null ? userF.getDateOfBirth() : LocalDate.parse( request.getDateOfBirth() ) )
                .number( request.getNumber() == null ? userF.getNumber() : request.getNumber() )
                .build();
        System.out.println(request);
        System.out.println(userN);

        // CONTROLLO USER AND ADMIN
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (username.equals(userF.getUsername()) || isAdmin()) {
            System.out.println("CONTROL PASS");
            return save( userN );
        } else {
            System.out.println("ERROR");
            throw new Exception("Impossibile modificare l'utente");
        }
    }

    // Ritorna true se l'utente è ADMIN come ruolo, falso se non lo è
    private boolean isAdmin() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = "";

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {

            for (GrantedAuthority authority : userDetails.getAuthorities()) {
                userRole = authority.getAuthority();

                System.out.println("Ruolo: " + userRole);
            }
        }

        return Objects.equals(userRole, "ADMIN");
    }

    // Ritorna true se l'utente è USER come ruolo, falso se non lo è
    private boolean isUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = "";

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {

            for (GrantedAuthority authority : userDetails.getAuthorities()) {
                userRole = authority.getAuthority();

                System.out.println("Ruolo: " + userRole);
            }
        }

        return Objects.equals(userRole, "USER");
    }

    // MAKE USER ADMIN
    public User updateUserAdmin(UserUpdateRequest request) throws Exception {

        User userF = userRepository.findById( request.getId() )
                .orElseThrow(() -> new Exception( "User not found"));

        userF.setRole(Role.ADMIN);
        userF.setAge(userF.calculateAge());

        // CONTROLLO ADMIN
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(isAdmin()) {
            return save( userF );
        }else {
            throw new Exception("Non si hanno i permessi");
        }


    }

//    // CONTROLLO ADMIN
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    String username = authentication.getName();
//
//        if (username.equals(userF.getUsername()) || isAdmin(authentication)) {
//        System.out.println("CONTROL PASS");
//        return save( userN );
//    } else {
//        System.out.println("ERROR");
//        throw new Exception("Impossibile modificare l'utente");
//    }

    // ELIMINA UTENTE
    public ResponseEntity<String> deleteByEmail( String email) {
        Optional<User> userF = userRepository.findByUsername(email);
        if (userF.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato!");
        } else {
            userRepository.delete(userF.get());
            return ResponseEntity.ok("Utente eliminato con successo!");
        }
    }
}
