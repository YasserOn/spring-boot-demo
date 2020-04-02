package com.xkcoding.netty.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatDTO implements Serializable {
    private String username;

    private String message;

}
