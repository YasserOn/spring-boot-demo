package com.xkcoding.mongodb.model;

import com.xkcoding.mongodb.annotation.SnowFlakeId;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "operate_log")
public class OperateLog implements Serializable {

    private static final long serialVersionUID = -1260750678250831213L;

    @Id
    @SnowFlakeId
    private Long id;

    private String name;

}
