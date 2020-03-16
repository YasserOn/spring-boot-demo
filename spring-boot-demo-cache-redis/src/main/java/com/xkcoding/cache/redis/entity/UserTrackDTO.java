package com.xkcoding.cache.redis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserTrackDTO extends UserTrack {
    private Long trackTime;

    public UserTrackDTO() {
    }

    public UserTrackDTO(UserTrack to, Double score) {
        this.spuId = to.getSpuId();
        this.userId = to.getUserId();
        this.companyId = to.getCompanyId();
        this.img = to.getImg();
        this.title = to.getTitle();
        this.trackTime = score.longValue();
    }

    @Override
    public String toString() {
        return "UserTrackDTO{" +
            "trackTime=" + trackTime +
            ", userId=" + userId +
            ", spuId=" + spuId +
            ", companyId=" + companyId +
            ", title='" + title + '\'' +
            ", img='" + img + '\'' +
            '}';
    }
}
