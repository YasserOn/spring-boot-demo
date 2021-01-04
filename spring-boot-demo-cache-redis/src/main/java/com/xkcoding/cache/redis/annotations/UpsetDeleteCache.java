package com.xkcoding.cache.redis.annotations;

import java.lang.annotation.*;

/**
 * @Desc:
 * @Author:cyw
 * @CreateTime: 2020/6/19 11:52
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface UpsetDeleteCache {

    String cacheKey()  default "";

}
