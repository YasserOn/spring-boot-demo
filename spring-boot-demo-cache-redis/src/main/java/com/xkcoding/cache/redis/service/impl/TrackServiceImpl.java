package com.xkcoding.cache.redis.service.impl;

import cn.hutool.json.JSONUtil;
import com.xkcoding.cache.redis.annotations.UpsetDeleteCache;
import com.xkcoding.cache.redis.entity.UserTrack;
import com.xkcoding.cache.redis.service.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TrackServiceImpl implements TrackService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Integer save(UserTrack userTrack) {
        return null;
    }

    @Override
    @CacheEvict(cacheNames = "demo",key = "#id")
    public Integer save(UserTrack userTrack, Integer id) {
        System.out.println("保存成功--->" + userTrack);
        log.info("保存成功---->" + userTrack);
        log.info("删除缓存---->");
        String s = stringRedisTemplate.opsForValue().get("demo.1");
        log.info("cache------->"+s);
        return 1;
    }

    @Override
    public UserTrack get() {
        UserTrack userTrack = new UserTrack().setSpuId(8888L).setUserId(1111111L);
        stringRedisTemplate.opsForValue().append("demo::1", JSONUtil.toJsonStr(userTrack));
        log.info("保存缓存---->");
        return userTrack;
    }
}
