package org.cyg.thinking.in.spring.dependency.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * ResolvableDependency 作为依赖来源
 */
public class ResolvableDependencySourceDemo {

    @Autowired
    private String value;

    @PostConstruct
    public void init() {
        System.out.println(value);
    }

    public static void main(String[] args) {
        // 创建 AnnotationConfigApplicationContext 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 将当前类 作为配置类 （Configuration Class）
        // 注册 Configuration Class （配置类）
        applicationContext.register(ResolvableDependencySourceDemo.class);
        applicationContext.addBeanFactoryPostProcessor( beanFactory -> {
            // 注册 ResolvableDependency
            beanFactory.registerResolvableDependency(String.class, "Hello, World");
        });
        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }
}
