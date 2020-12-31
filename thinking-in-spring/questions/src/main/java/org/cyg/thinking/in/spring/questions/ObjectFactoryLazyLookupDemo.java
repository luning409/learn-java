package org.cyg.thinking.in.spring.questions;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * {@link ObjectFactory} 延迟依赖查找示例
 * @see ObjectFactory
 * @see ObjectProvider
 */
public class ObjectFactoryLazyLookupDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册
        context.register(ObjectFactoryLazyLookupDemo.class);
        // 启动
        context.refresh();

        ObjectFactoryLazyLookupDemo demo = context.getBean(ObjectFactoryLazyLookupDemo.class);
        // userObjectFactory userObjectProvider
        System.out.println("userObjectFactory == userObjectProvider : " +
                (demo.userObjectFactory == demo.userObjectProvider));
        System.out.println("userObjectFactory.getClass() == userObjectProvider.getClass() : " +
                (demo.userObjectFactory.getClass() == demo.userObjectProvider.getClass()));
        // 实际对象
        System.out.println("user = " + demo.userObjectFactory.getObject());
        System.out.println("user = " + demo.userObjectProvider.getObject());
        System.out.println("userObjectFactory.getObject() == context.getBean(User.class) : "
                + (demo.userObjectFactory.getObject() == context.getBean(User.class)));
        // 关闭
        context.close();

    }
    @Autowired
    private ObjectFactory<User> userObjectFactory;
    @Autowired
    private ObjectProvider<User> userObjectProvider;

    @Bean
    @Lazy
    public static User user() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        return user;
    }
}
