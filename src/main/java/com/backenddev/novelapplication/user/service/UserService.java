package com.backenddev.novelapplication.user.service;

import com.backenddev.novelapplication.dtos.ReadingProgressDto;
import com.backenddev.novelapplication.dtos.UserDto;
import com.backenddev.novelapplication.execption.ApiNotFoundException;
import com.backenddev.novelapplication.execption.UserNotFoundException;
import com.backenddev.novelapplication.user.entity.ReadingProgress;
import com.backenddev.novelapplication.user.entity.User;
import com.backenddev.novelapplication.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository repo;
    @Autowired
    public UserService(UserRepository repo) {
        this.repo = repo;
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

    public void registerNewUser(UserDto userDto) {
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

    public User updateUser(String userName, UserDto user) {
        User foundUser = repo.findByUserName(userName);

        UserDto dto = new UserDto();
        foundUser.setUserName(dto.getUserName());
        foundUser.setPassword(dto.getPassword());
        foundUser.setProfilePicUrl(dto.getProfilePicUrl());
        return repo.save(foundUser);

    }

    public void deleteUser(String userName) {
        if (repo.existsByUserName(userName)) {
            repo.deleteByUserName(userName);
        }
        throw new UserNotFoundException("User not found");

    }

    public ReadingProgress updateReadingProgress(ReadingProgressDto progress) {
        return null;
    }

//    public ReadingProgress updateReadingProgress(Long userId, ReadingProgressDto progress) {
//        User user = getUserById(userId);
//        Optional<ReadingProgress> existingProgress = readingProgressRepository
//                .findByUserIdAndSeriesId(userId, progressDTO.getSeriesId());
//
//        ReadingProgress progress;
//        if (existingProgress.isPresent()) {
//            progress = existingProgress.get();
//            progress.setLastEpisodeRead(progressDTO.getLastEpisodeRead());
//        } else {
//            progress = new ReadingProgress();
//            progress.setUser(user);
//            progress.setSeriesId(progressDTO.getSeriesId());
//            progress.setLastEpisodeRead(progressDTO.getLastEpisodeRead());
//        }
//        return readingProgressRepository.save(progress);
//    }
}
