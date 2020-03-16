package com.xkcoding.cache.redis;

import com.xkcoding.cache.redis.entity.User;
import com.xkcoding.cache.redis.entity.UserTrack;
import com.xkcoding.cache.redis.entity.UserTrackDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>
 * Redis测试
 * </p>
 *
 * @package: com.xkcoding.cache.redis
 * @description: Redis测试
 * @author: yangkai.shen
 * @date: Created in 2018/11/15 17:17
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
public class RedisTest extends SpringBootDemoCacheRedisApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    private RedisTemplate<String, UserTrack> redisLogTemplate;

    @Autowired
    private RedisTemplate<String, Integer> luaTemplate;

    /**
     * 测试 Redis 操作
     */
    @Test
    public void get() {
        // 测试线程安全，程序结束查看redis中count的值是否为1000
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        IntStream.range(0, 1000).forEach(i -> executorService.execute(() -> stringRedisTemplate.opsForValue().increment("count", 1)));

        stringRedisTemplate.opsForValue().set("k1", "v1");
        String k1 = stringRedisTemplate.opsForValue().get("k1");
        log.debug("【k1】= {}", k1);

        // 以下演示整合，具体Redis命令可以参考官方文档
        String key = "xkcoding:user:1";
        redisTemplate.opsForValue().set(key, new User(1L, "user1"));
        // 对应 String（字符串）
        User user = (User) redisTemplate.opsForValue().get(key);
        log.debug("【user】= {}", user);
    }


    @Test
    public void zset() {
        UserTrack userTrack;
        Set<UserTrack> set;
        for (int i = 0; i < 150; i++) {
            userTrack = new UserTrack()
                .setSpuId((long) i)
                .setUserId((long) 1)
                .setTitle("标题" + i)
                .setImg("照片" + i)
                .setCompanyId(i % 2 == 0 ? 1818L : 7758258L)
            ;
            if (redisLogTemplate.opsForZSet().size("track-zset1") > 100) {
                redisLogTemplate.opsForZSet().removeRange("track-zset1", 0, 1);
            }
            redisLogTemplate.opsForZSet().add("track-zset" + userTrack.getUserId(), userTrack, System.currentTimeMillis());

//            redisLogTemplate.opsForList().rightPush("track-demo", userTrack);
//            redisLogTemplate.opsForList().trim("track-demo", -100, -1);
        }
    }


    @Test
    public void zsetLua() {
        UserTrack userTrack;
        Set<UserTrack> set;
        for (int i = 0; i < 150; i++) {

            if (luaTemplate.opsForZSet().size("track-zset1-lua") >= 100) {
                luaTemplate.opsForZSet().removeRange("track-zset1-lua", 0, 1);
            }
            luaTemplate.opsForZSet().add("track-zset1-lua", i, System.currentTimeMillis());

//            redisLogTemplate.opsForList().rightPush("track-demo", userTrack);
//            redisLogTemplate.opsForList().trim("track-demo", -100, -1);
        }
    }

    @Test
    public void getFromRedisZsetLua() {
        Set<Integer> range = luaTemplate.opsForZSet().reverseRange("track-zset1-lua", 0, -1);
        Set<ZSetOperations.TypedTuple<Integer>> typedTuples = luaTemplate.opsForZSet().reverseRangeWithScores("track-zset1-lua", 0, -1);
        range.forEach(System.out::println);
        System.out.println("=======================");
        System.out.println("=======================");
        System.out.println("=======================");
        System.out.println("=======================");
        System.out.println("=======================");
        new ArrayList<>(typedTuples)
            .forEach(System.out::println);
    }


    @Test
    public void getFromRedisZset() {
        Set<UserTrack> range = redisLogTemplate.opsForZSet().reverseRange("track-zset1", 0, -1);
        Set<ZSetOperations.TypedTuple<UserTrack>> typedTuples = redisLogTemplate.opsForZSet().reverseRangeWithScores("track-zset1", 0, -1);
        range.forEach(System.out::println);
        System.out.println("=======================");
        System.out.println("=======================");
        System.out.println("=======================");
        System.out.println("=======================");
        System.out.println("=======================");
        typedTuples.stream()
            .map(s->new UserTrackDTO(s.getValue(),s.getScore()))
            .collect(Collectors.toList())
            .forEach(System.out::println);
    }

    @Test
    public void getFromRedisZsetMatch() {
        Set<UserTrack> range = redisLogTemplate.opsForZSet().reverseRange("track-zset1", 0, -1);
        Set<ZSetOperations.TypedTuple<UserTrack>> typedTuples = redisLogTemplate.opsForZSet().reverseRangeWithScores("track-zset1", 0, -1);
        range.forEach(System.out::println);
        System.out.println("=======================");
        System.out.println("=======================");
        System.out.println("=======================");
        System.out.println("=======================");
        System.out.println("=======================");
        Map<Long, List<UserTrackDTO>> map = typedTuples.stream()
            .map(s -> new UserTrackDTO(s.getValue(), s.getScore()))
            .collect(Collectors.groupingBy(UserTrack::getCompanyId));
        System.out.println("=======================");
        System.out.println("=======================");
        System.out.println("=======================");
        System.out.println("========查看7758258公司的========");
        map.get(7758258L).forEach(System.out::println);
    }

    @Test
    public void list() {
        UserTrack userTrack;
        Set<UserTrack> set;
        for (int i = 500; i < 750; i++) {
            userTrack = new UserTrack()
                .setSpuId((long) i)
                .setUserId((long) i)
                .setTitle("标题" + i)
                .setImg("照片" + i)
                .setCompanyId(i % 2 == 0 ? 1818L : 7758258L)
            ;
            redisLogTemplate.opsForList().rightPush("track-demo", userTrack);
//            redisLogTemplate.opsForList().rightPush("track-demo", userTrack);
            redisLogTemplate.opsForList().trim("track-" + userTrack.getUserId(), -100, -1);
        }
    }

    @Test
    public void getFromRedis() {
        List<UserTrack> userTracks = redisLogTemplate.opsForList().range("track-demo", 0, -1);
        System.out.println("=============");
        System.out.println("=============");
        System.out.println("=============");
        System.out.println("=============");
    }
}
