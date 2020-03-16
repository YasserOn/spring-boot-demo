package com.xkcoding.cache.redis.controller;

import com.xkcoding.cache.redis.entity.UserTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/track")
public class SpuDetailController {

    @Autowired
    private RedisTemplate<String, UserTrack> redisLogTemplate;

    @GetMapping("/demo")
    public void demo() {
        UserTrack userTrack = new UserTrack().setSpuId(8888L).setUserId(1111111L);
        redisLogTemplate.opsForList().rightPush("track-demo", userTrack);
    }

}
