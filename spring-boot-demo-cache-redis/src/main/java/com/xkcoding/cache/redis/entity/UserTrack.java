package com.xkcoding.cache.redis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserTrack {

    protected Long userId;
    protected Long spuId;
    protected Long companyId;
    protected String title;
    protected String img;

}
