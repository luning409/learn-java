package org.cyg.thinking.in.spring.bean.definition;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * {@link org.springframework.beans.factory.config.BeanDefinition} 构建示例
 */
public class BeanDefinitionCreationDemo {

    public static void main(String[] args) {

        // 1. 通过 BeanDefinitionBuilder 构建
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        // 通过属性设置
        builder
                .addPropertyValue("id", 1)
                .addPropertyValue("name", "张三");
        // 获取 BeanDefinition 实例
        BeanDefinition beanDefinition = builder.getBeanDefinition();
        System.out.println(beanDefinition);
        // BeanDefinition 并非 Bean 终态， 可以自定义修改

        // 2. 通过 AbstractBeanDefinition 以及派生类
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        // 设置 Bean 类型
        genericBeanDefinition.setBeanClass(User.class);
        // 通过 MutablePropertyValues 批量操作属性
        MutablePropertyValues propertyValues = new MutablePropertyValues();
//        propertyValues.addPropertyValue("id", 1);
//        propertyValues.addPropertyValue("name", "张三");
        propertyValues
                .add("id", 1)
                .add("name", "张三");
        // 通过 set MutablePropertyValues 批量操作属性
        genericBeanDefinition.setPropertyValues(propertyValues);
    }
}
