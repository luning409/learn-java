package org.cyg.thinking.in.spring.dependency.lookup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * 通过 Object Provider 查找
 */
public class ObjectProviderDemo { // Configuration 是非必需注解

    private static final Log logger = LogFactory.getLog(ObjectProviderDemo.class);

    public static void main(String[] args) throws IOException {

        Properties props=new Properties();
        props.load(ObjectProviderDemo.class.getClassLoader() .getResourceAsStream("log4j.properties") );
        System.out.println(logger);

        logger.debug("test");
        // 创建 AnnotationConfigApplicationContext 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 不允许存在同名的
        applicationContext.setAllowBeanDefinitionOverriding(false);
        // 将当前类 ObjectProviderDemo 作为配置类 （Configuration Class）
        applicationContext.register(ObjectProviderDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
        lookupByObjectProvider(applicationContext);
        lookupIfAvailable(applicationContext);
        lookupBySteamOps(applicationContext);
        Object object = applicationContext.getBean("environment");
        Object message = applicationContext.getBean("message");
        System.out.println(object);
        System.out.println(message);
        // 关闭应用上下文
        applicationContext.close();
    }

    private static void lookupBySteamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
//        for (String string : objectProvider) {
//            System.out.println(string);
//        }
        objectProvider.stream().forEach(System.out::println);
    }

    private static void lookupIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        User user = userObjectProvider.getIfAvailable(User::createUser);
        System.out.println("当前 User 对象:" + user);

    }

    @Bean(name = "message")
    public Integer getInt() {
        return 10;
    }

    @Bean(name = "message")
    public String message() {
        return "Message";
    }

    @Bean
    @Primary
    public String hellWorld() {
        // 方法名就是 Bean 名称 = "hellWorld"
        return "Hello, World";
    }

    private static void lookupByObjectProvider(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        System.out.println(objectProvider.getObject());
    }
}
