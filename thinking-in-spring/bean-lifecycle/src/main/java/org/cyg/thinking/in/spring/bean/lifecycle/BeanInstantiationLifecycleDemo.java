package org.cyg.thinking.in.spring.bean.lifecycle;

import org.cyg.thinking.in.spring.ioc.overview.domain.SuperUser;
import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.ObjectUtils;

/**
 * Bean 实例化生命周期示例
 */
public class BeanInstantiationLifecycleDemo {

    public static void main(String[] args) {
        executeBeanFactory();
        System.out.println("----------------------------------");
        executeApplicationContext();
    }

    private static void executeBeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 方法一：添加 BeanPostProcessor 实现(实例)
        beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String[] locations = {"META-INF/dependency-lookup-context.xml", "META-INF/bean-constructor-dependency-injection.xml"};
        int beanNumbers = beanDefinitionReader.loadBeanDefinitions(locations);
        System.out.println("已加载 BeanDefinition 数量:" + beanNumbers);
        User user = beanFactory.getBean("user", User.class);
        System.out.println("user = " + user);
        User superUser = beanFactory.getBean("superUser", SuperUser.class);
        System.out.println("superUser = " + superUser);

        // 构造器注入按照类型注入, resolveDependency
        UserHolder userHolder = beanFactory.getBean("userHolder", UserHolder.class);
        System.out.println("userHolder = " + userHolder);
    }

    public static void executeApplicationContext() {
        String[] locations = {"META-INF/dependency-lookup-context.xml", "META-INF/bean-constructor-dependency-injection.xml"};
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
        applicationContext.setConfigLocations(locations);
        // 方法二：将 MyInstantiationAwareBeanPostProcessor 作为 Bean 注册， 在 META-INF/bean-constructor-dependency-injection.xml 配置文件中在

        // 启动 应用上下文
        applicationContext.refresh();

        User user = applicationContext.getBean("user", User.class);
        System.out.println("user = " + user);
        User superUser = applicationContext.getBean("superUser", SuperUser.class);
        System.out.println("superUser = " + superUser);

        // 构造器注入按照类型注入, resolveDependency
        UserHolder userHolder = applicationContext.getBean("userHolder", UserHolder.class);
        System.out.println("userHolder = " + userHolder);
        // 关闭应用上下文
        applicationContext.close();
    }


}

