package net.qqxh.sunflow;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈upms服务启动入口〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: FlowApplication.java
 * @date: 2019/5/29 20:51
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@SpringBootApplication
//Camunda配置注解
@EnableProcessApplication
public class FlowApplication {
    @Autowired
    private RuntimeService runtimeService;
    public static void main(String[] args) {
        SpringApplication.run(FlowApplication.class, args);
    }

}
