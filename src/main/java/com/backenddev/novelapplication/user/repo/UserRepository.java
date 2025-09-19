package com.backenddev.novelapplication.user.repo;

import com.backenddev.novelapplication.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

    User findByUserName(String userName);

    String id(Long id);

    User deleteUserById(Long id);

    boolean existsByUserName(String userName);

    void deleteByUserName(String userName);
}
