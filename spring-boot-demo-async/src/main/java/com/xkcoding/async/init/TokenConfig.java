package com.xkcoding.async.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @Desc:
 * @Author:cyw
 * @CreateTime: 2020/7/23 9:19
 **/
@Configuration
public class TokenConfig {


    @Bean
    @Lazy
    public TokenPojoLazyInit2 tokenPojoLazyInit2(){
        TokenPojoLazyInit2 token = new TokenPojoLazyInit2();

        return token.init();
    }

}





























