package com.backenddev.novelapplication.auth.controller;

import com.backenddev.novelapplication.dtos.UserRequestDto;
import com.backenddev.novelapplication.user.entity.User;
import com.backenddev.novelapplication.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService userService) {
        this.service= userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserRequestDto userDto) {
        try {
            service.registerNewUser(userDto);
            return new ResponseEntity<>("user registered successfully", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("failed to register user", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(service.verifyUserLogin(user), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("failed to login", HttpStatus.NOT_FOUND);
        }
    }

}
