package com.xkcoding.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * <p>
 * 文章实体类
 * </p>
 *
 * @package: com.xkcoding.mongodb.model
 * @description: 文章实体类
 * @author: yangkai.shen
 * @date: Created in 2018-12-28 16:21
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    /**
     * 文章id
     */
    @Id
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 点赞数量
     */
    private Long thumbUp;

    /**
     * 访客数量
     */
    private Long visits;


    private Boolean deleted;

    public Article(String title, String content, LocalDateTime createTime, LocalDateTime updateTime, Long thumbUp, Long visits) {
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.thumbUp = thumbUp;
        this.visits = visits;
    }
}
