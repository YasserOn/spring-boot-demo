package com.xkcoding.netty.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "socketio")
public class ChatConfig {
    private String host;

    private int port;

    private int bossCount;

    private int workCount;

    private Boolean allowCustomRequests;

    private int upgradeTimeout;

    private int pingTimeout;

    private int pingInterval;
}
