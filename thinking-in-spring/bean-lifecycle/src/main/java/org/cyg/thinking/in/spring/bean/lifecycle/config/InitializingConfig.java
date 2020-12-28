package org.cyg.thinking.in.spring.bean.lifecycle.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializingConfig implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingConfig#afterPropertiesSet");
    }
}
