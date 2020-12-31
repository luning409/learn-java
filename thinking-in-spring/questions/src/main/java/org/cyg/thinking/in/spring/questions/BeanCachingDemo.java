package org.cyg.thinking.in.spring.questions;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import java.util.Date;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * Bean 是否缓存示例
 */
public class BeanCachingDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册
        context.register(BeanCachingDemo.class);
        // 启动
        context.refresh();
        // BeanCachingDemo 是 Configuration Class, Singleton Scope Bean
        BeanCachingDemo demo = context.getBean(BeanCachingDemo.class);
        for (int i = 0; i < 9; i++) {
            // Singleton Scope Bean 是存在缓存
            System.out.println(demo == context.getBean(BeanCachingDemo.class));
        }

        User user = context.getBean(User.class);
        for (int i = 0; i < 9; i++) {
            // Scope prototype
            System.out.println(user == context.getBean(User.class));
        }

        System.out.println(context.getBean("user"));
        System.out.println(context.getBean(Date.class));
        // 关闭
        context.close();

    }

//    @Bean("user") // 如果使用已经存在的 user name 那么 @Bean 不会注册 , 如: @Bean("user") context.getBean(User.class) 会失败
//    public static Date userDate() {
//        return new Date();
//    }

    @Bean
    @Scope(SCOPE_PROTOTYPE) // 原型
    private static User user() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        return user;
    }

    @Bean
//    @Bean("user") // 如果使用已经存在的 user name 那么 @Bean 不会注册
    public static Date date() {
        return new Date();
    }

}
