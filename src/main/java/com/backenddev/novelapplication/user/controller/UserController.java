package com.backenddev.novelapplication.user.controller;

import com.backenddev.novelapplication.dtos.ReadingProgressDto;
import com.backenddev.novelapplication.dtos.UserRequestDto;
import com.backenddev.novelapplication.execption.UserNotFoundException;
import com.backenddev.novelapplication.user.entity.ReadingProgress;
import com.backenddev.novelapplication.user.entity.User;
import com.backenddev.novelapplication.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("api/v1/user")
public class UserController {


    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(HttpServletRequest request) {
        System.out.println("Session id: " + request.getSession().getId());
        return new ResponseEntity<>( service.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getUsersByName(@PathVariable String userName) {
        try {
            return new ResponseEntity<>(service.getUsersByName(userName), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserNotFoundException("User not found", e);
        }
    }

    @PutMapping("/updateUser/{userName}")
    public ResponseEntity<?> updateUser(@RequestParam String userName, @RequestBody UserRequestDto user) {
        try{
            service.updateUser(userName, user);
            return new ResponseEntity<>( "User updated", HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteUser/{userName}")
    public ResponseEntity<?> deleteUser (@PathVariable String userName) {
        try {
            service.deleteUser(userName);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("failed to delete user", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/readingProgress")
    public ResponseEntity<ReadingProgress> readingProgress(@RequestBody ReadingProgressDto progress){
        ReadingProgress readingProgress = service.updateReadingProgress(progress);
        return new ResponseEntity<>(readingProgress, HttpStatus.OK);
    }
}
