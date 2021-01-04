package com.xkcoding.async.init;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Desc:
 * @Author:cyw
 * @CreateTime: 2020/7/22 21:03
 **/
@Component
public class TokenPojoInit {

    @PostConstruct
    public void init(){
        System.out.println("正常加载");
    }

}





























