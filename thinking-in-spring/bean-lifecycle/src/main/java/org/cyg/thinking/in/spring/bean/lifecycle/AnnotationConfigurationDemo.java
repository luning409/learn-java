package org.cyg.thinking.in.spring.bean.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 注解 {@link Configuration} 初始化问题
 */
@Configuration
@ComponentScan("org.cyg.thinking.in.spring.bean.lifecycle")
public class AnnotationConfigurationDemo {

    public static void main(String[] args) {
        // 两个 @Configuration 注解的类， 一个实现 ApplicationContextAware 、 InitializingBean ,
        // 那么 InitializingBean#afterPropertiesSet 执行顺序 有可能比 ApplicationContextAware#setApplicationContext 方法 晚？
        // InitializingBean 和 ApplicationContextAware 的执行顺序是单个Bean 内的
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AnnotationConfigurationDemo.class);

        context.refresh();

        context.close();
    }

}
