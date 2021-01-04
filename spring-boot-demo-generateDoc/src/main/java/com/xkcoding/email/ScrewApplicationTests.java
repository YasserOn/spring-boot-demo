package com.xkcoding.email;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ScrewApplicationTests {

   @Autowired
   ApplicationContext applicationContext;

   @Test
   void contextLoads() {
      DataSource dataSourceMysql = applicationContext.getBean(DataSource.class);

      // 生成文件配置
      EngineConfig engineConfig = EngineConfig.builder()
            // 生成文件路径，自己mac本地的地址，这里需要自己更换下路径
            .fileOutputDir("/Users/mac/Desktop")
            // 打开目录
            .openOutputDir(false)
            // 文件类型
            .fileType(EngineFileType.HTML)
            // 生成模板实现
            .produceType(EngineTemplateType.freemarker).build();

      // 生成文档配置（包含以下自定义版本号、描述等配置连接）
      Configuration config = Configuration.builder()
            .version("1.0.3")
            .description("生成文档信息描述")
            .dataSource(dataSourceMysql)
            .engineConfig(engineConfig)
            .produceConfig(getProcessConfig())
            .build();

      // 执行生成
      new DocumentationExecute(config).execute();
   }


   /**
    * 配置想要生成的表+ 配置想要忽略的表
    * @return 生成表配置
    */
   public static ProcessConfig getProcessConfig(){
      // 忽略表名
      List<String> ignoreTableName = Arrays.asList("aa","test_group");
      // 忽略表前缀，如忽略a开头的数据库表
      List<String> ignorePrefix = Arrays.asList("a","t");
      // 忽略表后缀
      List<String> ignoreSuffix = Arrays.asList("_test","czb_");

      return ProcessConfig.builder()
            //根据名称指定表生成
            .designatedTableName(new ArrayList<>())
            //根据表前缀生成
            .designatedTablePrefix(new ArrayList<>())
            //根据表后缀生成
            .designatedTableSuffix(new ArrayList<>())
            //忽略表名
            .ignoreTableName(ignoreTableName)
            //忽略表前缀
            .ignoreTablePrefix(ignorePrefix)
            //忽略表后缀
            .ignoreTableSuffix(ignoreSuffix).build();
   }




    @Test
    public static void generateDoc() {
        documentGeneration();
    }

    /**
     * 文档生成
     */
    static void documentGeneration() {
        //数据源
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://111.231.110.136:3306/eluyun-pms-test");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("rGB5noD8stuk7f");
        //设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        DataSource dataSource = new HikariDataSource(hikariConfig);
        //生成配置
        EngineConfig engineConfig = EngineConfig.builder()
            //生成文件路径
            .fileOutputDir("C:\\Users\\EDZ\\Desktop\\cyw\\ddll")
            //打开目录
            .openOutputDir(true)
            //文件类型
            .fileType(EngineFileType.HTML)
            //生成模板实现
            .produceType(EngineTemplateType.freemarker)
            //自定义文件名称
            .fileName("数据库生成文档").build();

        /*忽略表*/
        ArrayList<String> ignoreTableName = new ArrayList<>();
//        ignoreTableName.add("test_user");
//        ignoreTableName.add("test_group");
        /*忽略表前缀*/
        ArrayList<String> ignorePrefix = new ArrayList<>();
//        ignorePrefix.add("test_");
        /*忽略表后缀*/
        ArrayList<String> ignoreSuffix = new ArrayList<>();
//        ignoreSuffix.add("_test");

        /*需要生成的数据表名*/
        List<String> targetTableList = new ArrayList<>();
        targetTableList.add("elc_lost_villa_activity");
        ProcessConfig processConfig = ProcessConfig.builder()
            //指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
            //根据名称指定表生成
            .designatedTableName(targetTableList)
            //根据表前缀生成
            .designatedTablePrefix(new ArrayList<>())
            //根据表后缀生成
            .designatedTableSuffix(new ArrayList<>())
            //忽略表名
            .ignoreTableName(ignoreTableName)
            //忽略表前缀
            .ignoreTablePrefix(ignorePrefix)
            //忽略表后缀
            .ignoreTableSuffix(ignoreSuffix).build();
        //配置
        Configuration config = Configuration.builder()
            //版本
            .version("1.0.0")
            //描述
            .description("数据库设计文档生成")
            //数据源
            .dataSource(dataSource)
            //生成配置
            .engineConfig(engineConfig)
            //生成配置
            .produceConfig(processConfig)
            .build();
        //执行生成
        new DocumentationExecute(config).execute();
    }
}
