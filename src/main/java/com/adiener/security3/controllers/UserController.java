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

    @PutMapping("/modify") // SOLO PROPRETARIO ACCOUNT E ADMIN
    public ResponseEntity<User> updateUser( @RequestBody UserUpdateRequest request ) throws Exception {
        return ResponseEntity.ok(userService.creatAndUpdateUserAdmin(request));
    }

    @PutMapping("make-admin")
    public ResponseEntity<User> makeUserAdmin(@RequestBody UserUpdateRequest request) throws Exception {
        return ResponseEntity.ok(userService.updateUserAdmin(request));
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable("email") String email) throws Exception {
        return userService.deleteByEmail( email );
    }
}
