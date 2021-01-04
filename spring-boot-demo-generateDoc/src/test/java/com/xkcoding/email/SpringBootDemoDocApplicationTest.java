package com.xkcoding.email;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.xkcoding.email.entity.GenerateTableDocConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author:cyw
 * @CreateTime: 2020/9/27 20:33
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemoDocApplicationTest {

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${generate.datasource.jdbcUrl}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Value("${generate.engine.fileOutputDir}")
    private String fileOutputDir;

    @Value("${generate.process.tableName}")
    private List<String> generateTableName;

    @Test
    public void generateDoc() {
        GenerateTableDocConfig config =GenerateTableDocConfig.buildDateSourceConfig(
            new GenerateTableDocConfig(), driverClassName, jdbcUrl, username, password);

        //生成配置
        EngineConfig engineConfig = EngineConfig.builder()
            //生成文件路径
            .fileOutputDir(fileOutputDir)
            //打开目录
            .openOutputDir(true)
            //文件类型
            .fileType(EngineFileType.WORD)
            //生成模板实现
            .produceType(EngineTemplateType.freemarker)
            //自定义文件名称
            .fileName("数据库生成文档").build();
        config.setEngineConfig(engineConfig);

        /*需要生成的数据表名*/
        ProcessConfig processConfig = ProcessConfig.builder()
            //指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
            //根据名称指定表生成
            .designatedTableName(generateTableName)
            //根据表前缀生成
            .designatedTablePrefix(new ArrayList<>())
            //根据表后缀生成
            .designatedTableSuffix(new ArrayList<>())
            //忽略表名
            .ignoreTableName(new ArrayList<>())
            //忽略表前缀
            .ignoreTablePrefix(new ArrayList<>())
            //忽略表后缀
            .ignoreTableSuffix(new ArrayList<>()).build();
        config.setProcessConfig(processConfig);



        documentGeneration(config);

    }

    /**
     * 文档生成
     */
    static void documentGeneration(GenerateTableDocConfig generateConfig) {

        //填充数据库信息
        HikariConfig hikariConfig = buildHikariDataSourceInfo(generateConfig);

        //设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        DataSource dataSource = new HikariDataSource(hikariConfig);

        //配置
        Configuration config = Configuration.builder()
            //版本
            .version("1.0.0")
            //描述
            .description("数据库设计文档")
            //数据源
            .dataSource(dataSource)
            //生成配置
            .engineConfig(generateConfig.getEngineConfig())
            //生成配置
            .produceConfig(generateConfig.getProcessConfig())
            .build();
        //执行生成
        new DocumentationExecute(config).execute();
    }

    private static HikariConfig buildHikariDataSourceInfo(GenerateTableDocConfig config) {
        GenerateTableDocConfig.DateSourceConfig dateSourceConfig = config.getDateSourceConfig();
        if (dateSourceConfig == null) {
            throw new IllegalArgumentException("数据源信息为空");
        }
        //数据源
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(dateSourceConfig.getDriverClassName());
        hikariConfig.setJdbcUrl(dateSourceConfig.getJdbcUrl());
        hikariConfig.setUsername(dateSourceConfig.getUsername());
        hikariConfig.setPassword(dateSourceConfig.getPassword());
        return hikariConfig;
    }
}
