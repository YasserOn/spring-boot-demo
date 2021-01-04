package com.xkcoding.async.init;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Desc:
 * @Author:cyw
 * @CreateTime: 2020/7/22 21:03
 **/
public class TokenPojoLazyInit2 {

    public TokenPojoLazyInit2 init(){
        System.out.println("懒加载1");
        return this;
    }

}





























