package com.xkcoding.sms.config;

import com.xkcoding.sms.constant.SMSConstant;
import com.xkcoding.sms.service.SMSService;
import com.xkcoding.sms.service.impl.ALiSMSServiceImpl;
import com.xkcoding.sms.service.impl.RongLianSMSServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(SMSProperties.class)
public class SMSConfig {

    private final SMSProperties smsProperties;

    @Bean
    public SMSService init(){
        switch (smsProperties.getActivePlatform()){
            case (SMSConstant.RONGLIAN):
                return getRongLianServiceImpl(smsProperties.getRonglian());
            case (SMSConstant.ALIBABA):
                return getALiServiceImpl(smsProperties.getAli());
            default:
                throw new RuntimeException("当前短信网关版本暂不支持  "+smsProperties.getActivePlatform());
        }
    }

    private SMSService getALiServiceImpl(SMSProperties.ALiConfig aLiConfig) {
        return new ALiSMSServiceImpl(aLiConfig);
    }

    private SMSService getRongLianServiceImpl(SMSProperties.RongLianConfig rongLianConfig) {
        return new RongLianSMSServiceImpl(rongLianConfig);
    }

}
