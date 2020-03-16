package com.xkcoding.cache.redis.service.impl;

import com.xkcoding.cache.redis.entity.UserTrack;
import com.xkcoding.cache.redis.service.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TrackServiceImpl implements TrackService {

    @Override
    public Integer save(UserTrack userTrack) {
        System.out.println("保存成功--->" + userTrack);
        log.info("保存成功---->" + userTrack);
        return 1;
    }
}
