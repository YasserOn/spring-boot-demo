package com.xkcoding.netty.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.io.Serializable;

@Data
public class MessageDTO implements Serializable {
    private String username;
    private String message;

    public MessageDTO() {
    }

    public MessageDTO(String beanStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        MessageDTO dto = new MessageDTO() ;
        try {
            dto = objectMapper.readValue(beanStr, MessageDTO.class);
            this.message = dto.getMessage();
            this.username = dto.getUsername();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
