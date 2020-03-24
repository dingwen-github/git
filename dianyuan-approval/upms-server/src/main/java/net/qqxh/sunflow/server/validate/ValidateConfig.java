package net.qqxh.sunflow.server.validate;

import net.qqxh.common.validate.aspect.ValidateAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateConfig {
    @Bean
    public ValidateAspect validateAspect() {
        ValidateAspect validateAspect = new ValidateAspect();
        return validateAspect;
    }
}
