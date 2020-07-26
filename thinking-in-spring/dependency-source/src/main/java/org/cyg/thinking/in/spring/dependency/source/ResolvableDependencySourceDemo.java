package org.cyg.thinking.in.spring.dependency.source;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;
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
        // 刷新后，再注册, 会重置 DefaultListableBeanFactory.frozenBeanDefinitionNames 为 null
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(Integer.class);
        builder.addConstructorArgValue(123);
        applicationContext.registerBeanDefinition("test", builder.getBeanDefinition());
        Integer test = applicationContext.getBean("test", Integer.class);
        System.out.println(test);
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println("bean name:" + beanDefinitionName);
        }
        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }

}
