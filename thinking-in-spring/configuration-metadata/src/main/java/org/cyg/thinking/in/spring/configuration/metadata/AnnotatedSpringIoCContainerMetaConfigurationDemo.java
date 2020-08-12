package org.cyg.thinking.in.spring.configuration.metadata;

import org.cyg.thinking.in.spring.ioc.overview.domain.SuperUser;
import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.cyg.thinking.in.spring.ioc.overview.enums.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

/**
 * 基于 Java 注解 Spring IoC 容器元信息配置示例 <br/>
 * 将当前类作为 Configuration Class
 */
@ImportResource("classpath:META-INF/dependency-lookup-context.xml")
@Import({User.class, SuperUser.class})
@PropertySource("classpath:META-INF/user-bean-definitions.properties") // Java 8+ @Repeatable 支持, 读取多个文件
@PropertySource("classpath:META-INF/user-bean-definitions.properties")
// Java 8 - 以前的写法
//@PropertySources(value = {@PropertySource("classpath:META-INF/user-bean-definitions.properties"), @PropertySource("classpath:META-INF/user-bean-definitions.properties")})
public class AnnotatedSpringIoCContainerMetaConfigurationDemo {

    /***
     * user.name 是 Java Properties 默认存在, 当前操作用户: chenyg,
     * 而非配置文件中定义"法外狂徒:张三"
     */
    @Bean
    public User configuredUser(@Value("${myUser.id}") Long id,
                               @Value("${user.name}") String name,
                               @Value("${myUser.city}") City city,
                               @Value("${myUser.workCities}") City[] workCities,
                               @Value("${myUser.lifeCities}") List<City> lifeCities,
                               @Value("${myUser.configFileLocation}") Resource configFileLocation
                               ) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setCity(city);
        user.setCity(city);
        user.setWorkCities(workCities);
        user.setLifeCities(lifeCities);
        user.setConfigFileLocation(configFileLocation);
        return user;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册当前类 作为 Configuration Class
        context.register(AnnotatedSpringIoCContainerMetaConfigurationDemo.class);
        // 启动应用上下文
        context.refresh();
        // beanName  和 bean 映射
        Map<String, User> usersMap = context.getBeansOfType(User.class);
        for (Map.Entry<String, User> entry: usersMap.entrySet()) {
            System.out.printf("User Bean Name : %s , content: %s %n", entry.getKey(), entry.getValue());
        }
        //  关闭 Spring 应用上下文
        context.close();
    }
}
