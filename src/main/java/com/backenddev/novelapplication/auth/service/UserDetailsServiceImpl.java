package com.backenddev.novelapplication.auth.service;


import com.backenddev.novelapplication.auth.entity.UserPrincipal;
import com.backenddev.novelapplication.user.entity.User;
import com.backenddev.novelapplication.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * loads users from the database for authentication
 * **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private final UserRepository userRepo;

    public UserDetailsServiceImpl (UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    //loads user by userName for authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);
        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }

        return new UserPrincipal(user);

    }
}
