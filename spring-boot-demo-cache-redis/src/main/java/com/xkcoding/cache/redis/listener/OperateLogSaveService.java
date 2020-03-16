//package com.xkcoding.cache.redis.listener;
//
//import com.xkcoding.cache.redis.entity.Track;
//import com.xkcoding.cache.redis.service.TrackService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.QueryTimeoutException;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//@Component
//@Slf4j
//@SuppressWarnings("rawtypes")
//public class OperateLogSaveService {
//    @Autowired
//    private RedisTemplate<String, Track> redisLogTemplate;
//    @Autowired
//    private TrackService trackService;
//
//    private ExecutorService listenerExecutor;
//    private ExecutorService saveExecutor;
//
//    @PostConstruct
//    public void init() {
//        listenerExecutor = Executors.newSingleThreadExecutor(new CustomizableThreadFactory("OPERATE_LOG_LISTENER"));
//        saveExecutor = Executors.newFixedThreadPool(2, new CustomizableThreadFactory("OPERATE_LOG_SAVE_THREAD"));
//
//        listenerExecutor.submit(this::listenRedisOperateLog);
//    }
//
//    private void listenRedisOperateLog() {
//        while (true) {
//            try {
//                System.out.println("监听");
//                Track track = redisLogTemplate.opsForList().leftPop("track-demo", 0, TimeUnit.SECONDS);
//                if (track == null) {
//                    log.error("redis用户操作日志监听异常，返回了空日志");
//                    continue;
//                }
//                saveExecutor.submit(() -> trackService.save(track));
//            } catch (QueryTimeoutException e) {
//                log.info("redis用户操作日志监听超时");
//            } catch (Exception e) {
//                log.error("redis用户操作日志监听异常", e);
//            }
//        }
//    }
//}
