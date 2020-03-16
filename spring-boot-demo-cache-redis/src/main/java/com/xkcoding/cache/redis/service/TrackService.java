package com.xkcoding.cache.redis.service;

import com.xkcoding.cache.redis.entity.UserTrack;

public interface TrackService {
    Integer save(UserTrack userTrack);
}
