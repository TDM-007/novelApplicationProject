package com.backenddev.novelapplication.dtos;
import com.backenddev.novelapplication.enums.UserRole;
import com.backenddev.novelapplication.user.entity.ReadingProgress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private UserRole role;
    private String profilePicUrl;
    private List<ReadingProgress> readingProgress;
}
