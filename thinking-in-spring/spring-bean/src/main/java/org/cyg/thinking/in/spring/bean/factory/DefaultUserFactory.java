package org.cyg.thinking.in.spring.bean.factory;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 默认 {@link UserFactory} 实现
 */
public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {
    @Autowired
    private User user;

    // 1.基于 @PostConstruct 注解
    @PostConstruct
    public void init() {
        System.out.println("@PostConstruct : DefaultUserFactory 初始化中...");
    }

    public void initUserFactory() {
        System.out.println("自定义初始化方法 initUserFactory() : DefaultUserFactory 初始化中...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet() : DefaultUserFactory 初始化中...");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("@PreDestroy : DefaultUserFactory 销毁中...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destroy() : DefaultUserFactory 销毁中...");
    }

    public void doDestroy() {
        System.out.println("自定义销毁方法 doDestroy() : DefaultUserFactory 销毁中...");
    }

    @Override
    public void finalize() throws Throwable {
        System.out.println("当前 DefaultUserFactory 对象正在被回收...");
    }
}
