package com.xkcoding.netty.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.DataListener;
import com.xkcoding.netty.entity.ChatDTO;
import com.xkcoding.netty.entity.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ChatEventListener {
    @Autowired
    private SocketIOServer server;

    public ChatEventListener() {
    }

    @OnEvent(value = "chat-message")
    public void onEvent(MessageDTO message) {
        log.debug("{}", message);
        this.server.getBroadcastOperations().sendEvent("chatevent", message);
    }

    @OnEvent("chatevent")
    public void onChatEvent(ChatDTO data) throws Exception {
        log.debug("{}", data);
        this.server.getBroadcastOperations().sendEvent("chatevent", data);
    }
}
