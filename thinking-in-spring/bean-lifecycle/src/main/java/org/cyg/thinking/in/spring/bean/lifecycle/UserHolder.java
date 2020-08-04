package org.cyg.thinking.in.spring.bean.lifecycle;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * User Holder  类
 */
public class UserHolder implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware,
        EnvironmentAware, InitializingBean, SmartInitializingSingleton, DisposableBean {

    private final User user;

    private Integer number;

    private String description;

    private ClassLoader classLoader;

    private BeanFactory beanFactory;

    private String beanName;

    private Environment environment;

    public UserHolder(User user) {
        this.user = user;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 依赖于注解驱动
     * 当前场景：BeanFactory
     */
    @PostConstruct
    public void initPostConstruct() {
        // postProcessBeforeInitialization V3 -> initPostConstruct V4
        this.description = "The user holder V4";
        System.out.println("initPostConstruct() = " + description);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // initPostConstruct V4 -> afterPropertiesSet V5
        this.description = "The user holder V5";
        System.out.println("afterPropertiesSet() = " + description);
    }

    /**
     * 自定义初始化方法
     */
    public void init() {
        // afterPropertiesSet V5 -> init V6
        this.description = "The user holder V6";
        System.out.println("init() = " + description);
    }

    @PreDestroy
    public void preDestroy() {
        // MyDestructionAwareBeanPostProcessor.postProcessBeforeDestruction The use holder V9
        // -> The use holder V10
        this.description = "The user holder V10";
        System.out.println("preDestroy() : " + this.description);
    }

    @Override
    public void destroy() throws Exception {
        // this.preDestroy The use holder V10
        // -> The use holder V11
        this.description = "The user holder V11";
        System.out.println("destroy() : " + this.description);
    }

    public void doDestroy() {
        // this.preDestroy The use holder V11
        // -> The use holder V12
        this.description = "The user holder V12";
        System.out.println("doDestroy() : " + this.description);
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                ", number=" + number +
                ", description='" + description + '\'' +
                ", beanName='" + beanName + '\'' +
                '}';
    }

    // BeanNameAware, BeanClassLoaderAware, BeanFactoryAware 的调用是通过以下源码
    // org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeAwareMethods
    // 通过上面方法的源码,会按顺序调用调用 setBeanName setBeanClassLoader setBeanFactory
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    // EnvironmentAware 的调用是通过以下源码
    // org.springframework.context.support.ApplicationContextAwareProcessor.invokeAwareInterfaces
    // 通过上面方法的源码,会按顺序调用调用 setEnvironment
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void afterSingletonsInstantiated() {
        // MyInstantiationAwareBeanPostProcessor.postProcessAfterInitialization V7 -> afterSingletonsInstantiated V8
        this.description = "The use holder V8";
        System.out.println("afterSingletonsInstantiated() = " + description);
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("UserHolder is finalize...");
    }
}
