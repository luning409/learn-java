package org.cyg.thinking.in.spring.bean.definition;

import org.cyg.thinking.in.spring.bean.factory.DefaultUserFactory;
import org.cyg.thinking.in.spring.bean.factory.UserFactory;
import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.Map;

/**
 * Bean 初始化 Demo
 */
@Configuration // 可用 可不用 表示 Configuration class (配置类)
public class BeanInitializationDemo {

    public static void main(String[] args) {
        // 创建 AnnotationConfigApplicationContext 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类）
        applicationContext.register(BeanInitializationDemo.class);

        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 非延迟初始化在  Spring 应用上下文启动完成后，被初始化
        System.out.println("Spring 应用上下文已启动... ");
        // 依赖查找
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);
        System.out.println(userFactory);
        System.out.println("Spring 应用上下文准备关闭...");
        // 关闭 Spring 应用上下文
        applicationContext.close();
        System.out.println("Spring 应用上下文已关闭...");
        // 使用 removeBeanDefinition 销毁
//        Map<String, UserFactory> userFactoryMap = applicationContext.getBeansOfType(UserFactory.class);
//        for ( String key : userFactoryMap.keySet()) {
//            applicationContext.removeBeanDefinition(key);
//        }

    }

    @Bean(initMethod = "initUserFactory", destroyMethod = "doDestroy")
    @Lazy(value = false)
    public UserFactory userFactory() {
        return new DefaultUserFactory();
    }

    @Bean(initMethod = "init")
    @Lazy
    public User getUser() {
        return new User();
    }
}
