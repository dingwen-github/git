package net.qqxh.sunflow.server.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * Copyright (C), 2019-2020, sunflow开发团队
 *
 * 〈代码生成工具类〉<br>
 *
 * @author jwy
 * @fileName: CodeGenerator
 * @date: 23/05/2019 15:08
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CodeGenerator {
    private static final String WORKSPACE = "C:\\Users\\zhs\\IdeaProjects\\";
    private static final String TABLENAME = "supms_user_dept";
    private static final String CODE_MODEL_PATH = "net.qqxh.sunFlow.server.upms";

    public static void main(String[] args) {

        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //调整输出文件的路径
        gc.setOutputDir(WORKSPACE + "sunFlow\\upms-server\\src\\main\\java");
        gc.setFileOverride(true);
        // 不需要ActiveRecord特性的请改为false
        gc.setActiveRecord(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        // 作者
        gc.setAuthor("jason");
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setUrl("jdbc:mysql://localhost:3306/sunFlow?characterEncoding=utf-8&useSSL=false");
        mpg.setDataSource(dsc);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //添加了lombok注解，@Data直接注解在类上，去掉了getter和setter方法
        strategy.setEntityLombokModel(true);
        //生成@RestController
        strategy.setRestControllerStyle(true);
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //驼峰形式
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategy.setInclude(new String[]{TABLENAME});
        // 此处可以修改为您的表前缀 例如表f_datadictionary，
        // 这边加上f_ 生成后的类目就是DatadictionaryXXX，这边为空就生成类名FDatadictionaryXXX
        strategy.setTablePrefix(new String[]{"supms_"});
        strategy.setSuperServiceClass(null);
        strategy.setSuperServiceImplClass(null);
        //自定义继承的Mapper类全称，带包名
        strategy.setSuperMapperClass("net.qqxh.sunFlow.mapper.SuperMapper");
        strategy.setSuperControllerClass("net.qqxh.sunFlow.server.common.BaseController");
        mpg.setStrategy(strategy);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(CODE_MODEL_PATH);
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setEntity("bean");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);
        // 执行生成
        mpg.execute();
    }
}