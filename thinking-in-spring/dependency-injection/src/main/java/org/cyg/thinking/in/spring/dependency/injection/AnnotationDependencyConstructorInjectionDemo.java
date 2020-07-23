package org.cyg.thinking.in.spring.dependency.injection;


import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 基于 Java 注解的依赖注入 Constructor 注入示例
 */
public class AnnotationDependencyConstructorInjectionDemo {

    public static void main(String[] args) {
        // 创建 AnnotationConfigApplicationContext 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 作为配置类 （Configuration Class）
        // 注册 Configuration Class （配置类）
        applicationContext.register(AnnotationDependencyConstructorInjectionDemo.class);
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源， 解析并且生成 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);
        // 启动 Spring 应用上下文
        applicationContext.refresh();

        UserHolder userHolder = applicationContext.getBean(UserHolder.class);
        System.out.println(userHolder);
        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }

    @Bean
    public UserHolder userHolder(@Qualifier("user") User user) {
        return new UserHolder(user);
    }
}
