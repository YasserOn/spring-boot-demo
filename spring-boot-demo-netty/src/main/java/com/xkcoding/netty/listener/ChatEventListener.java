package com.xkcoding.netty.listener;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.xkcoding.netty.entity.MessageDTO;
import com.xkcoding.netty.mapper.ChatMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChatEventListener {
    @Autowired
    private SocketIOServer server;
    @Autowired
    private ChatMapper chatMapper;

    @OnEvent("chatevent")
    public void onChatEvent(MessageDTO data) throws Exception {
        log.debug("{}", data);
        int i = chatMapper.saveUser(data);
        this.server.getBroadcastOperations().sendEvent("chatevent", data);
    }
}
