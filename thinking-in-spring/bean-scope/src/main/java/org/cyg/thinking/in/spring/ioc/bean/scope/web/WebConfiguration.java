package org.cyg.thinking.in.spring.ioc.bean.scope.web;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Web MVC 配置类
 * 启动 打包好后的 可执行war 命令:
 * java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9527
 * bean-scope/target/bean-scope-1.0-SNAPSHOT-war-exec.jar
 */
@Configuration
@EnableWebMvc
public class WebConfiguration {

    @Bean
//    @RequestScope
//    @SessionScope
    @ApplicationScope
    public User user() {
        User user = new User();
        user.setId(1L);
        user.setName("法外狂徒:张三");
        return user;
    }
}
