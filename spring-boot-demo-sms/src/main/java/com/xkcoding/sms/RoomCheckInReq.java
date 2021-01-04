package com.xkcoding.sms;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: cyw
 * @Date: 2020/8/17 16:00
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RoomCheckInReq {


    private Integer id;
    private Date date;

}
