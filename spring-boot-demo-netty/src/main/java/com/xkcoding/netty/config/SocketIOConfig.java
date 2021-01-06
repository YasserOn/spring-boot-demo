package com.xkcoding.netty.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.protocol.JacksonJsonSupport;
import io.netty.channel.epoll.Epoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig implements CommandLineRunner {

    @Autowired
    private ChatConfig chatConfig;

    @Autowired
    private SocketIOServer server;


    /**
     * 以下配置在上面的application.properties中已经注明
     * @return
     */
    @Bean
    public SocketIOServer socketIOServer() {
//        SocketConfig socketConfig = new SocketConfig();
//        socketConfig.setTcpNoDelay(true);
//        socketConfig.setSoLinger(0);
//        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
//        config.setSocketConfig(socketConfig);
//        config.setHostname(chatConfig.getHost());
//        config.setPort(chatConfig.getPort());
//        config.setBossThreads(chatConfig.getBossCount());
//        config.setWorkerThreads(chatConfig.getWorkCount());
//        config.setAllowCustomRequests(chatConfig.getAllowCustomRequests());
//        config.setUpgradeTimeout(chatConfig.getUpgradeTimeout());
//        config.setPingTimeout(chatConfig.getPingTimeout());
//        config.setPingInterval(chatConfig.getPingInterval());
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setPort(chatConfig.getPort());
        config.setUseLinuxNativeEpoll(Epoll.isAvailable());
        config.setJsonSupport(new JacksonJsonSupport());
        config.setBossThreads(1);
        config.setWorkerThreads(1);
        return new SocketIOServer(config);
    }

    @Bean
    public BeanPostProcessor socketIOBeanPostProcessor(SocketIOServer server) {
        return new SpringAnnotationScanner(server);
    }

    @Override
    public void run(String... args) throws Exception {
        this.server.start();
    }
}
