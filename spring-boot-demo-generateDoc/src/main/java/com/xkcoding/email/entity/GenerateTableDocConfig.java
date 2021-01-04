package com.xkcoding.email.entity;

import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.process.ProcessConfig;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author:cyw
 * @CreateTime: 2020/9/30 15:19
 **/
@Data
@Accessors(chain = true)
public class GenerateTableDocConfig {

    /**
     * 生成文档数据库表名
     */
    private List<String> generateTables;
    /**
     * 文件生成配置
     */
    private EngineConfig engineConfig;

    /**
     * 数据处理
     */
    private ProcessConfig processConfig;

    /**
     * 数据库相关信息配置
     */
    private DateSourceConfig dateSourceConfig;
    @Data
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class DateSourceConfig{
        private String driverClassName;
        private String jdbcUrl;
        private String username;
        private String password;

        public DateSourceConfig(String driverClassName, String jdbcUrl, String username, String password) {
            this.driverClassName = driverClassName;
            this.jdbcUrl = jdbcUrl;
            this.username = username;
            this.password = password;
        }
    }

    public static GenerateTableDocConfig buildDateSourceConfig(GenerateTableDocConfig config, String driverClassName, String jdbcUrl, String username, String password) {
        DateSourceConfig dateSourceConfig = new DateSourceConfig(driverClassName, jdbcUrl, username, password);
        return config.setDateSourceConfig(dateSourceConfig);
    }



}





























