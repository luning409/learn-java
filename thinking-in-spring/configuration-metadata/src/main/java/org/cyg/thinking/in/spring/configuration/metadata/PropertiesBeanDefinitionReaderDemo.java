package org.cyg.thinking.in.spring.configuration.metadata;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.*;
import org.springframework.core.io.support.EncodedResource;

import java.nio.charset.StandardCharsets;

/**
 * {@link PropertiesBeanDefinitionReader} 示例
 */
public class PropertiesBeanDefinitionReaderDemo {

    public static void main(String[] args) {
        // 创建 IoC 底层容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 创建 面向 Properties 资源的 BeanDefinitionReader 示例
        // Properties 资源加载默认通过 ISO-8859-1, 实际存储 UTF-8
        String location = "META-INF/user-bean-definitions.properties";
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        // 通过指定的 ClassPath 获取 Resource 对象
        Resource resource = resourceLoader.getResource(location);
        // 转换成带有字符编码 EncodedResource 对象
        EncodedResource encodedResource = new EncodedResource(resource, StandardCharsets.UTF_8);
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(beanFactory);
        int beanDefinitionsCount = reader.loadBeanDefinitions(encodedResource);
        System.out.printf("已加载 %d 个 BeanDefinition%n", beanDefinitionsCount);

        // 通过依赖查找获取User Bean
        User user = beanFactory.getBean("myUser", User.class);
        System.out.println(user);

    }
}
