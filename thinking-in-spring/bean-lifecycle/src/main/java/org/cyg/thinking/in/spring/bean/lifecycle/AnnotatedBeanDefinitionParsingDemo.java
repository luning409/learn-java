package org.cyg.thinking.in.spring.bean.lifecycle;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注解 BeanDefinition 解析示例
 */
public class AnnotatedBeanDefinitionParsingDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 基于 Java 注解的 AnnotatedBeanDefinitionReader 的实现
        AnnotatedBeanDefinitionReader beanDefinitionReader = new AnnotatedBeanDefinitionReader(beanFactory);
        int beanDefinitionCountBefore = beanFactory.getBeanDefinitionCount();
        // 注册当前类 (非 @Component Class)
        beanDefinitionReader.register(AnnotatedBeanDefinitionParsingDemo.class);
        int beanDefinitionCountAfter = beanFactory.getBeanDefinitionCount();
        int beanDefinitionCount = beanDefinitionCountAfter - beanDefinitionCountBefore;
        System.out.println("已加载 BeanDefinition 数量:" + beanDefinitionCount);
        // 普通的Class 作为 Component 注册到 Spring IoC 容器后, 通常 Bean 名称为 annotatedBeanDefinitionParsingDemo
        // Bean 名称生成来自于 BeanNameGenerator，注解实现 AnnotationBeanNameGenerator
        AnnotatedBeanDefinitionParsingDemo demo = beanFactory.getBean("annotatedBeanDefinitionParsingDemo",
                AnnotatedBeanDefinitionParsingDemo.class);
        System.out.println(demo);
    }
}
