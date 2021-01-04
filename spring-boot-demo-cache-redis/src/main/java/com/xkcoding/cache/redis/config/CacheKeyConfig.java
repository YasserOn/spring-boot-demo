package com.xkcoding.cache.redis.config;


public class CacheKeyConfig {

    /**
     * 建议使用的缓存key前缀
     */
    private static final String SPRING_CACHE_KEY_PRIFIX = "CACHE.NAMES";

    /**
     * 建议使用缓存名字或key的分隔符
     */
    private static final String SPRING_CACHE_DELIMITER = ".";

    public static String getNtwConfigCacheKey(Integer ntwId) {
        return buildKeyPrifix("NtwConfig").append(ntwId).toString();
    }


    private static StringBuilder buildKeyPrifix(String cacheName) {
        StringBuilder sb = new StringBuilder(SPRING_CACHE_KEY_PRIFIX);
        sb.append(SPRING_CACHE_DELIMITER);
        sb.append(cacheName);
        sb.append(SPRING_CACHE_DELIMITER);
        return sb;
    }


}
