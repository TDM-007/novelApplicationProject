package com.backenddev.novelapplication.dtos;

import com.backenddev.novelapplication.user.entity.User;
import lombok.Data;

@Data
public class ReadingProgressDto {
    private Long seriesId;
    private int lastEpisode;
    private User user;
}
