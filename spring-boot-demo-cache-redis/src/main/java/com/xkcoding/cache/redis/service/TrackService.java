package com.xkcoding.cache.redis.service;

import com.xkcoding.cache.redis.entity.UserTrack;
import org.springframework.cache.annotation.CacheEvict;

public interface TrackService {
    Integer save(UserTrack userTrack);

    Integer save(UserTrack userTrack, Integer id);

    UserTrack get();
}
