package org.cyg.thinking.in.spring.ioc.overview.injection;

import org.cyg.thinking.in.spring.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 依赖注入实例
 */
public class DependencyInjectionDemo {

    public static void main(String[] args) {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
//        BeanFactory applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");

        // 依赖来源一： 自定义 Bean
        UserRepository userRepository = applicationContext.getBean("userRepository", UserRepository.class);
//        System.out.println(userRepository.getUsers());
        // 依赖来源二： 依赖注入 （容器内建依赖 ）
        System.out.println(userRepository.getBeanFactory());
        // 为什么这个表达式不会成立
        System.out.println(applicationContext == userRepository.getBeanFactory());
        ObjectFactory userFactory = userRepository.getObjectFactory();
        System.out.println(userFactory.getObject());
        System.out.println(userFactory.getObject() == applicationContext);
        // 依赖查找出现异常
        // NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.beans.factory.BeanFactory' available
//        System.out.println(applicationContext.getBean(BeanFactory.class));
        // 依赖来源三：容器内建 Bean
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("获取 Environment 类型的 Bean" + environment);
        whoIsContainer(userRepository, applicationContext);
    }

    private static void whoIsContainer(UserRepository userRepository, ApplicationContext applicationContext) {
        // 为什么这个表达式不会成立， 组合的方式， 接口上是 extend
        System.out.println(applicationContext == userRepository.getBeanFactory());

        // ApplicationContext is BeanFactory
        // The BeanFactory interface provides an advanced configuration mechanism capable of managing any type of object.
        // ApplicationContext is a sub-interface of BeanFactory.
    }

}
