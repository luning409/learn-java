package org.cyg.thinking.in.spring.dependency.injection;

import org.cyg.thinking.in.spring.dependency.injection.annotation.UserGroup;
import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Map;

/**
 * {@link Qualifier} 注解依赖注入
 */
public class QualifierAnnotationDependencyInjectionDemo {

    @Autowired
    private User user; // superUser -> primary = true
    @Autowired
    @Qualifier("user") // 指定 Bean 名称 或 ID
    private User namedUser;

    // 整体应用上下文存在 4 个 User类型的Bean:
    // superUser
    // user
    @Autowired
    private Collection<User> allUser; // 2 Beans = uer + superUser
    @Autowired
    @Qualifier
    private Collection<User> qualifierUsers; // 2 Bean = user1 + user2 -> 4 Beans = user1 + user2 + user3 + user4
    @Autowired
    @UserGroup
    private Collection<User> groupedUsers; // 2 Bean = user3 + user4
    @Autowired
    @Qualifier("namedQualifier")
    private Collection<User> namedQualifierUsers; // 2 Bean = user5 + user6
    @Autowired
    private ObjectProvider<User> objectProvider;
    /**
     * 分组限定
     * @return
     */
    @Bean
    @Qualifier // 进行逻辑分组
    public User user1() {
        return createUser(7L);
    }
    @Bean
    @Qualifier
    public User user2() {
        return createUser(8L);
    }
    @Bean
    @UserGroup
    public User user3() {
        return createUser(9L);
    }
    @Bean
    @UserGroup
    public User user4() {
        return createUser(10L);
    }

    @Bean
    @Qualifier("namedQualifier")
    public User user5() {
        return createUser(11L);
    }
    @Bean
    @Qualifier("namedQualifier")
    public User user6() {
        return createUser(12L);
    }


    private static User createUser(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }
    public static void main(String[] args) {
        // 创建 AnnotationConfigApplicationContext 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 作为配置类 （Configuration Class）
        // 注册 Configuration Class （配置类）
        applicationContext.register(QualifierAnnotationDependencyInjectionDemo.class);
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源， 解析并且生成 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);
        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 依赖查找 QualifierAnnotationDependencyInjectionDemo Bean
        QualifierAnnotationDependencyInjectionDemo demo = applicationContext.getBean(QualifierAnnotationDependencyInjectionDemo.class);
        // 期待输出 superUser Bean
        System.out.println("demo.user = " + demo.user);
        // 期待输出 user Bean
        System.out.println("demo.namedUser = " + demo.namedUser);
        //  期待输出 superUser user Bean
        System.out.println("demo.allUser = " + demo.allUser);
        // 期待输出 user1 user2 添加 @UserGroup 后 扩展成为了 4个Beans = user1 + user2 + user3 + user4
        System.out.println("demo.qualifierUsers = " + demo.qualifierUsers);
        // 期待输出 user3 user4
        System.out.println("demo.groupedUsers = " + demo.groupedUsers);
        // 期待输出 user5 user6
        System.out.println("demo.namedQualifierUsers = " + demo.namedQualifierUsers);
        // 依赖查找 所有 UserBeans
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        Map<String, User> userMap = beanFactory.getBeansOfType(User.class);
        // 依赖注入的时候只存在两个
        System.out.println("依赖注入 User Bean 总数:" + demo.objectProvider.stream().count());;
        // 依赖查找存在 8个
        ObjectProvider<User> objectProvider = beanFactory.getBeanProvider(User.class);
        System.out.println("依赖查找 User Bean 总数:" + objectProvider.stream().count());
        System.out.println(userMap);
        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }
}
