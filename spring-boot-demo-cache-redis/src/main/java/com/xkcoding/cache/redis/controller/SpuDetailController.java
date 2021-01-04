package com.xkcoding.cache.redis.controller;

import com.xkcoding.cache.redis.entity.UserTrack;
import com.xkcoding.cache.redis.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/track")
public class SpuDetailController {

    @Autowired
    private RedisTemplate<String, UserTrack> redisLogTemplate;
    @Autowired
    private TrackService trackService;

    @GetMapping("/demo")
    public void demo() {
        UserTrack userTrack = new UserTrack().setSpuId(8888L).setUserId(1111111L);
        redisLogTemplate.opsForList().rightPush("track-demo", userTrack);
    }

    @PostMapping("/save")
    public void save() {
        UserTrack userTrack = new UserTrack().setSpuId(8888L).setUserId(1111111L);
        trackService.save(userTrack, 1);
    }

    @GetMapping("/get")
    public UserTrack get() {
        return trackService.get();
    }

}
