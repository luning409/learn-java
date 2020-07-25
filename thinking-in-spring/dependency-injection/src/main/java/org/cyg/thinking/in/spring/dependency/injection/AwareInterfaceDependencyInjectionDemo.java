package org.cyg.thinking.in.spring.dependency.injection;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 基于 {@link Aware}  接口回调的依赖注入示例
 */
public class AwareInterfaceDependencyInjectionDemo implements BeanFactoryAware, ApplicationContextAware, BeanNameAware {

    private static BeanFactory beanFactory;

    private static ApplicationContext applicationContext;

    private static String beanName;

    public static void main(String[] args) {
        // 创建 AnnotationConfigApplicationContext 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 作为配置类 （Configuration Class） --> Spring Bean
        // 注册 Configuration Class （配置类）
        applicationContext.register(AwareInterfaceDependencyInjectionDemo.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();

        System.out.println(beanName);
        System.out.println(AwareInterfaceDependencyInjectionDemo.applicationContext == applicationContext);
        System.out.println(AwareInterfaceDependencyInjectionDemo.beanFactory == applicationContext.getBeanFactory());
        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AwareInterfaceDependencyInjectionDemo.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AwareInterfaceDependencyInjectionDemo.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        AwareInterfaceDependencyInjectionDemo.beanName = name;
    }
}
