package com.adiener.security3.services;

import com.adiener.security3.models.User;
import com.adiener.security3.repository.UserRepository;
import com.adiener.security3.requestModels.UserUpdateRequest;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public User creatAndUpdate( UserUpdateRequest request) throws Exception {
        User userF = userRepository.findById( request.getId() )
                .orElseThrow(() -> new Exception( "User not found"));
        User userN = User.builder()
                // default
                .id( userF.getId() )
                .email( userF.getEmail() )
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
        return save( userN );
    }

    // elimina utente
    public ResponseEntity<String> deleteByEmail( String email) {
        Optional<User> userF = userRepository.findByEmail(email);
        if (userF.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato!");
        } else {
            userRepository.delete(userF.get());
            return ResponseEntity.ok("Utente eliminato con successo!");
        }
    }
}
