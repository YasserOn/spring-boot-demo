package com.xkcoding.async.init;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Desc:
 * @Author:cyw
 * @CreateTime: 2020/7/22 21:03
 **/
@Lazy
@Component
public class TokenPojoLazyInit1 {

    @PostConstruct
    public void init(){
        System.out.println("懒加载1");
    }

}





























