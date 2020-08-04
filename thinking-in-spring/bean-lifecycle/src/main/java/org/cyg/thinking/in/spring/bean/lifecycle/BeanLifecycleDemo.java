package org.cyg.thinking.in.spring.bean.lifecycle;

import org.cyg.thinking.in.spring.ioc.overview.domain.SuperUser;
import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

import java.util.concurrent.TimeUnit;

public class BeanLifecycleDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 方法一：添加 BeanPostProcessor 实现(实例)
        // BeanFactory 无法通过 在 配置文件中添加 Bean， 然后 加载 BeanPostProcessor
        // 因为只有在应用上下中才会执行以下方法
        // org.springframework.context.support.AbstractApplicationContext.registerBeanPostProcessors
        beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());
        // 添加 MyDestructionAwareBeanPostProcessor 执行销毁前回调
        beanFactory.addBeanPostProcessor(new MyDestructionAwareBeanPostProcessor());
        // 添加 CommonAnnotationBeanPostProcessor 解决 @PostConstruct 回调
        beanFactory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());

        // 如果先注册的 BeanPostProcessor.postProcessBeforeInitialization 其中一个返回 null， 则不会执行剩下的 BeanPostProcessor
        // 具体逻辑在方法
        // org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String[] locations = {"META-INF/dependency-lookup-context.xml", "META-INF/bean-constructor-dependency-injection.xml"};
        int beanNumbers = beanDefinitionReader.loadBeanDefinitions(locations);
        System.out.println("已加载 BeanDefinition 数量:" + beanNumbers);
        // 显示地执行 preInstantiateSingletons
        // SmartInitializingSingleton 通常在 Spring ApplicationContext 场景使用
        // preInstantiateSingletons 将已注册的 BeanDefinition 初始化成 Spring Bean
        // 在 org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization
        // 调用 以下方法
        // org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons
        // 方法中， 调用getBean方法，提前初始化 Bean
        beanFactory.preInstantiateSingletons();

        User user = beanFactory.getBean("user", User.class);
        System.out.println("user = " + user);
        User superUser = beanFactory.getBean("superUser", SuperUser.class);
        System.out.println("superUser = " + superUser);

        // 构造器注入按照类型注入, resolveDependency
        UserHolder userHolder = beanFactory.getBean("userHolder", UserHolder.class);
        System.out.println("userHolder = " + userHolder);
        //  执行 Bean 销毁 (容器内)
        beanFactory.destroyBean("userHolder", userHolder);
        // Bean 销毁并不意味着 Bean 垃圾回收了
        System.out.println("userHolder = " + userHolder);
        userHolder = null;
        // 销毁 BeanFactory 中的单例 Bean
        beanFactory.destroySingletons();
        // 强制 GC
        System.gc();
        // 等待一段时间
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 强制 GC
        System.gc();
    }

}
