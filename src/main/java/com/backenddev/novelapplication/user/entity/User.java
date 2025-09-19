package com.backenddev.novelapplication.user.entity;

import com.backenddev.novelapplication.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "users_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, unique = true)
    private String userName;

    @Column (nullable = false, unique = true)
    private String password;

    @Column (nullable = false)
    @Enumerated (EnumType.STRING)
    private UserRole role;


    private String profilePicUrl;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ReadingProgress> readingProgress = new ArrayList<>(); //containers means arrays.




}
