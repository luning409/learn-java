package org.cyg.thinking.in.spring.dependency.injection;

import org.cyg.thinking.in.spring.dependency.injection.annotation.InjectedUser;
import org.cyg.thinking.in.spring.dependency.injection.annotation.MyAutowired;
import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.*;

import static org.springframework.context.annotation.AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME;

/**
 * 注解驱动的依赖注入处理过程
 */
public class AnnotationDependencyInjectionResolutionDemo {

    @Autowired
    @Lazy
    private User lazyUser;
    @Autowired
    private User user; // DependencyDescriptor ->
    // 必须(required = true)
    // 实时注入(eager = true)
    // 通过类型（User.class）
    // 字段名称("user")
    // 是否首要（primary = true）
    @Autowired         // 集合类型依赖注入
    private Collection<User> users; // user superUser

    @MyAutowired
    private Optional<User> userOptional; // superUser

    @Inject // JSR-330
    private User injectUser; // superUser
    @InjectedUser
    private User myInjectUser;

    /**
     * 非 static 时, 依赖 当前 AnnotationDependencyInjectionResolutionDemo 类注册
     * static 时, 提早于 AnnotationDependencyInjectionResolutionDemo 类注册
     *
     * @return
     */
//    @Bean(name = AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
//    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
//        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
//        // @Autowired + 新注解 @InjectUser
//        Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>(Arrays.asList(Autowired.class,
//                Inject.class, InjectedUser.class));
//        beanPostProcessor.setAutowiredAnnotationTypes(autowiredAnnotationTypes);
//        return beanPostProcessor;
//    }

    /**
     * 新、老的 AnnotationType 同时存在
     * @return
     */
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE - 3)
    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setAutowiredAnnotationType(InjectedUser.class);
        return beanPostProcessor;
    }

    public static void main(String[] args) {
        // 创建 AnnotationConfigApplicationContext 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 作为配置类 （Configuration Class）
        // 注册 Configuration Class （配置类）
        applicationContext.register(AnnotationDependencyInjectionResolutionDemo.class);
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源， 解析并且生成 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);
        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 依赖查找 QualifierAnnotationDependencyInjectionDemo Bean
        AnnotationDependencyInjectionResolutionDemo demo = applicationContext.getBean(AnnotationDependencyInjectionResolutionDemo.class);
        // lazyUser 是一个代理对象
        System.out.println("demo.lazyUser = " + demo.lazyUser);
        // 期待输出 superUser Bean
        System.out.println("demo.user = " + demo.user);
        // 期待输出 superUser Bean
        System.out.println("demo.injectUser = " + demo.injectUser);
        // 期待输出 user superUser Bean
        System.out.println("demo.users = " + demo.users);
        // 期待输出 superUser Bean
        System.out.println("demo.userOptional = " + demo.userOptional);
        // 期待输出 superUser Bean
        System.out.println("demo.myInjectUser = " + demo.myInjectUser);
        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }
}
