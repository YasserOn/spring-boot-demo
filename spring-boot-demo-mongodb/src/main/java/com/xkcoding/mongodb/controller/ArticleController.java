package com.xkcoding.mongodb.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.ImmutableMap;
import com.xkcoding.mongodb.core.MongoDbOperator;
import com.xkcoding.mongodb.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private MongoTemplate template;
    @Autowired
    private Snowflake snowflake;
    @Autowired
    private com.xkcoding.mongodb.repository.ArticleRepository articleRepository;

    private MongoDbOperator<Article> mongoDbOperator;

    @PostConstruct
    void init() {
        mongoDbOperator = new MongoDbOperator<>(template, Article.class);
    }

    /**
     * 使用MongoTemplate查询数据
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(String title) {


        return articleRepository.findByTitleLike(title).toString();
    }

    @RequestMapping(value = "/list2", method = RequestMethod.GET)
    public String list2(String title) {
        Article model = mongoDbOperator.getOne(ImmutableMap.of("title", title));

        return model.toString();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        Article article = new Article(snowflake.nextId(), RandomUtil.randomString(20), RandomUtil.randomString(150), LocalDateTime.now(), LocalDateTime.now(), 0L, 0L,false);
        mongoDbOperator.insert(article);

        return article.toString();
    }

}
