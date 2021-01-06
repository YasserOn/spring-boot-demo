package com.xkcoding.netty.mapper;

import com.xkcoding.netty.entity.MessageDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * <p>
 * User Mapper
 * </p>
 *
 * @package: com.xkcoding.orm.mybatis.mapper
 * @description: User Mapper
 * @author: yangkai.shen
 * @date: Created in 2018/11/8 10:54
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Mapper
@Component
public interface ChatMapper {


    @Insert({"insert into chat(username,message) values(#{messageDTO.username}, #{messageDTO.message})" })
    int saveUser(@Param("messageDTO") MessageDTO messageDTO);


}
