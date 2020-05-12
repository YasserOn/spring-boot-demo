package com.xkcoding.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "sms")
public class SMSProperties {

    private String activePlatform;

    private RongLianConfig ronglian;

    private ALiConfig ali;

    @Data
    public static class RongLianConfig{
        private String appId;
        private String accountSid;
        private String accountToken;
        private Map<String,String> template;
    }
    @Data
    public static class ALiConfig{
        private String accessKeyId;
        private String accessKeySecret;
        private String signName;
        private Map<String, ALiTemplate> template;

        @Data
        public static class ALiTemplate{
            private String code;
            private List<String> paramList;
        }
    }
}
