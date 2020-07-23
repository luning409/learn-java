package org.cyg.thinking.in.spring.dependency.injection;


import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 基于 Java 注解的依赖注入 字段 注入示例
 */
public class AnnotationDependencyFieldInjectionDemo {

    @Autowired
    private
//    static // @Autowired 会忽略掉静态字段
    UserHolder userHolder;
    @Resource
//    static // Caused by: java.lang.IllegalStateException: @Resource annotation is not supported on static fields
    private  UserHolder userHolder2;

    public static void main(String[] args) {
        // 创建 AnnotationConfigApplicationContext 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 作为配置类 （Configuration Class）
        // 注册 Configuration Class （配置类）
        applicationContext.register(AnnotationDependencyFieldInjectionDemo.class);
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源， 解析并且生成 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);
        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 依赖查找 AnnotationDependencyFieldInjectionDemo Bean
        AnnotationDependencyFieldInjectionDemo demo = applicationContext.getBean(AnnotationDependencyFieldInjectionDemo.class);
        // @Autowired 自动关联
        UserHolder userHolder = demo.userHolder;
        System.out.println(userHolder);
        System.out.println(demo.userHolder2);
        System.out.println(userHolder == demo.userHolder2);
        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }

    @Bean
    public UserHolder userHolder(@Qualifier("superUser") User user) {
        return new UserHolder(user);
    }
}
