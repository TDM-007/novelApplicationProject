package com.backenddev.novelapplication.dtos;

import com.backenddev.novelapplication.enums.UserRole;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDto {
    @NotNull(message = "Username required")
    @Size(min = 3, max = 30, message = "username must between 3 to 30 characters long")
    private String userName;

    @NotNull (message = "passowrd required")
    @Size (min = 6, message = "password must me more that 6 characters long")
    private  String password;

    private String userEmail;

    private UserRole role;

    private String profilePicUrl;
}
