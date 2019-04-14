package com.jokerdata.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

public class CodeGeneratorCustom {


    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        final String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/jokerdata-generatordmin/src/main/java");
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setAuthor("oldMa");
        //生成文件名:
        gc.setXmlName("%sCustomMapper");
        gc.setMapperName("%sCustomMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sImpl");
        gc.setControllerName("%sController");
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
        packageConfig.setController("controller");
        packageConfig.setEntity("entity.app.generator");
        packageConfig.setMapper("mapper.app.generator");
        packageConfig.setService("service.app");
        packageConfig.setServiceImpl("service.app.impl");
        packageConfig.setXml("xml");
        mpg.setPackageInfo(packageConfig);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        // 自定义 xxList.jsp 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
//        focList.add(new FileOutConfig("/template/list.jsp.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输入文件名称
//                return "D://my_" + tableInfo.getEntityName() + ".jsp";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);

        // 调整 Entry 生成目录
        focList.add(new FileOutConfig("templates/entity.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/jokerdata-generator/src/main/java/com/jokerdata/entity/app/generator/"
                        + tableInfo.getEntityName() + ".java";
            }
        });

        // 调整 mapper 生成目录
        focList.add(new FileOutConfig("templates/mapper.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/jokerdata-generator/src/main/java/com/jokerdata/mapper/app/generator/"
                        + tableInfo.getMapperName() + ".java";
            }
        });
        // 调整 xml 生成目录
        focList.add(new FileOutConfig("templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/jokerdata-generator/src/main/resources/mapper/app/generator/"
                        + tableInfo.getMapperName() + ".xml";
            }
        });

        // 调整 controller 生成目录
//        focList.add(new FileOutConfig("templates/controller.java.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return projectPath+"/jokerdata-generator/src/main/java/com/jokerdata/controller/app/"
//                        + tableInfo.getControllerName() + ".java";
//            }
//        });
        // 调整 service 生成目录
        focList.add(new FileOutConfig("templates/service.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/jokerdata-generator/src/main/java/com/jokerdata/service/app/"
                        + tableInfo.getServiceName() + ".java";
            }
        });
        // 调整 serviceImp 生成目录
        focList.add(new FileOutConfig("templates/serviceImpl.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/jokerdata-generator/src/main/java/com/jokerdata/service/app/impl/"
                        + tableInfo.getServiceImplName() + ".java";
            }
        });

        // 调整 api 生成目录
//        focList.add(new FileOutConfig("templates/vue/api.js.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return projectPath+"/jokerdata-generator/src/main/java/com/jokerdata/vue/js/"
//                        +tableInfo.getEntityName() + ".js";
//            }
//        });
//        // 调整 list 生成目录
//        focList.add(new FileOutConfig("templates/vue/index.vue.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return projectPath+"/jokerdata-generator/src/main/java/com/jokerdata/vue/"
//                        +"index" + ".vue";
//            }
//        });


        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        TemplateConfig tc = new TemplateConfig();
        tc.setController(null);
        tc.setService(null);
        tc.setServiceImpl(null);
        tc.setMapper(null);
        tc.setXml(null);
        tc.setEntity(null);
        mpg.setTemplate(tc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix(new String[]{"yj_", "gy_", "d_", "t", "h_", "g_"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(null);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setSuperControllerClass(null);
        strategy.setLogicDeleteFieldName("del");
        strategy.setInclude(new String[]{"g_ad"});
//        strategy.setSuperEntityColumns("tid");
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}