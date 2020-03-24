package net.qqxh.sunflow.server.mybatis;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈mybatis plus配置〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: MybatisPlusConfig.java
 * @date: 2019/5/29 20:35
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Configuration
@MapperScan("net.qqxh.**.mapper")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}