package com.jokerdata.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGenerator {


    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        final String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/jokerdata-generator/src/main/java");
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setAuthor("oldMa");
        //生成文件名:
        gc.setXmlName("%sMapper");
        gc.setMapperName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sImpl");
//        gc.setEntityName("%sBean");
//        gc.setControllerName("%sController");
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://121.43.186.222:3306/share?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("weipai");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.jokerdata");
//        packageConfig.setController("controller");
        packageConfig.setEntity("entity.app.generator");
        packageConfig.setMapper("mapper.app.generator");
        packageConfig.setXml("xml.mapping");
        mpg.setPackageInfo(packageConfig);
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        // 自定义 xxList.jsp 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        // 调整 xml 生成目录
        focList.add(new FileOutConfig("templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/jokerdata-generator/src/main/resources/mapper/app/generator/"
                        + tableInfo.getMapperName() + ".xml";
            }
        });

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix(new String[]{"yj_", "gy_", "d_", "t", "h_", "g_"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(null);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setSuperControllerClass(null);
        strategy.setInclude(new String[]{"g_task_log","g_task"});
//        strategy.setSuperEntityColumns("tid");
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}