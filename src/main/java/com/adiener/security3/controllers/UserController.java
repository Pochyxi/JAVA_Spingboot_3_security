package com.adiener.security3.controllers;

import com.adiener.security3.models.User;
import com.adiener.security3.requestModels.UserUpdateRequest;
import com.adiener.security3.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/modify")
    @PreAuthorize( "hasRole('USER')" )
    public ResponseEntity<User> updateUser( @RequestBody UserUpdateRequest request ) throws Exception {
        return ResponseEntity.ok(userService.creatAndUpdate(request));
    }

    @DeleteMapping("/delete/{email}")
    @PreAuthorize( "hasRole('USER')" )
    public ResponseEntity<String> deleteUser(@PathVariable("email") String email) throws Exception {
        return userService.deleteByEmail( email );
    }
}
