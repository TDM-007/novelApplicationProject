package com.backenddev.novelapplication.dtos;


import lombok.Data;

@Data
public class LoginDto {
//    @NotNull(message = "passowrd required")
    private  String password;

//    @NotNull(message = "email required")
//    @Email(message = "email must be valid")
    private String email;
}
