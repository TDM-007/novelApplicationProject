package com.backenddev.novelapplication.user.service;


import com.backenddev.novelapplication.auth.service.JWTService;
import com.backenddev.novelapplication.dtos.ReadingProgressDto;
import com.backenddev.novelapplication.dtos.UserRequestDto;
import com.backenddev.novelapplication.execption.ApiNotFoundException;
import com.backenddev.novelapplication.execption.BadRequestException;
import com.backenddev.novelapplication.execption.UserNotFoundException;
import com.backenddev.novelapplication.user.entity.ReadingProgress;
import com.backenddev.novelapplication.user.entity.User;
import com.backenddev.novelapplication.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository repo;
    //this is so we encode the password.
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Autowired
    AuthenticationManager authManager;

    private JWTService jwtService;

    @Autowired
    public UserService(UserRepository repo, JWTService jwtService) {
        this.repo = repo;
        this.jwtService = jwtService;
    }


    public List<User> getAllUsers() {
        List<User> user = repo.findAll();
        return user;
    }

    public User getUsersByName(String userName) {
        if (repo.existsByUserName(userName)) {
            return repo.findByUserName(userName);
        }
        throw new UserNotFoundException("User not found");

    }


    public User updateUser(String userName, UserRequestDto dto) {
        //find the user in the repo
        User foundUser = repo.findByUserName(userName);

        //map the userDto the user entity
        foundUser.setUserName(dto.getUserName());
        foundUser.setPassword(dto.getPassword());
        foundUser.setProfilePicUrl(dto.getProfilePicUrl());
        //save the found user to the database.
        return repo.save(foundUser);

    }

    public void deleteUser(String userName) {
        if (userName == null || userName.equals("")) {
            throw new BadRequestException("userName cannot be empty");
        }
        if (repo.existsByUserName(userName)) {
            repo.deleteByUserName(userName);
        }
        throw new UserNotFoundException("User not found");

    }

    public ReadingProgress updateReadingProgress(ReadingProgressDto progress) {
        return null;
    }

    //verify if users are authenticated.
    public String verifyUserLogin( User user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
        );

        if (repo.existsByUserName(user.getUserName())) {
              if(authentication.isAuthenticated()){
                  return jwtService.generateToken(user.getUserName());// .generates token embeds the username and optional claims.
              }
                throw new UserNotFoundException("failed to authenticate user.");

        }
        throw new BadRequestException("user doesn't exist");
    }

    //Register a new user
    public void registerNewUser(UserRequestDto userDto) {

        //encoding the password so no one else will know it except the user.
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        if (repo.existsByUserName(userDto.getUserName())){
            throw new ApiNotFoundException("userName already exists");
        }
//        if (repo.existsByEmail(userDto.getEmail())){
//            throw new ApiNotFoundException("email already exists");
//        }

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setProfilePicUrl(userDto.getProfilePicUrl());
        user.setRole(userDto.getRole());
        repo.save(user);

    }
}
