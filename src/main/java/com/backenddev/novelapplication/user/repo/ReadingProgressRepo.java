package com.backenddev.novelapplication.user.repo;

import com.backenddev.novelapplication.user.entity.ReadingProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingProgressRepo extends JpaRepository<ReadingProgress, Long> {
}
