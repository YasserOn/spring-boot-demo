package com.xkcoding.netty.entity;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatDTO {
    private String userName;

    private String content;

}
