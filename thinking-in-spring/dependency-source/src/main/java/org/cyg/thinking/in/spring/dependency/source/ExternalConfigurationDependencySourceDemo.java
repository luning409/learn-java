package org.cyg.thinking.in.spring.dependency.source;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 *  外部化配置作为依赖来源示例
 */
@PropertySource(value = {"classpath:/META-INF/default.properties", "classpath:/META-INF/default.yml"}, encoding = "UTF-8")
@Configuration
public class ExternalConfigurationDependencySourceDemo {

    @Value("${user.id:-1}")
    private Long id;
    /**
     * 当配置为${user.name}, 会输出我的电脑用户名称: chenyg
     * 这是由于配置化优先级的原因
     *
     */
    @Value("${usr.name}")
    private String name;
    @Value("${user.resource:classpath:/default.properties}")
    private Resource resource;

    public static void main(String[] args) {
        // 创建 AnnotationConfigApplicationContext 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 将当前类 作为配置类 （Configuration Class）
        // 注册 Configuration Class （配置类）
        applicationContext.register(ExternalConfigurationDependencySourceDemo.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 依赖查找 ExternalConfigurationDependencySourceDemo
        ExternalConfigurationDependencySourceDemo demo = applicationContext.getBean(ExternalConfigurationDependencySourceDemo.class);

        System.out.println("demo.id = " + demo.id);
        System.out.println("demo.name = " + demo.name);
        System.out.println("demo.resource = " + demo.resource);
        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }
}
