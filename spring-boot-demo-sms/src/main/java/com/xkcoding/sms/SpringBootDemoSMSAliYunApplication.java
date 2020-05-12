package com.xkcoding.sms;

import com.xkcoding.sms.config.SMSProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SMSProperties.class)
public class SpringBootDemoSMSAliYunApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoSMSAliYunApplication.class, args);
    }

}
