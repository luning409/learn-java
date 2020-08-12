package org.cyg.thinking.in.spring.configuration.metadata;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.cyg.thinking.in.spring.ioc.overview.enums.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外部化配置示例
 */
@PropertySource("classpath:META-INF/user-bean-definitions.properties") // Java 8+ @Repeatable 支持, 读取多个文件
public class PropertySourceDemo {

    /***
     * user.name 是 Java Properties 默认存在, 当前操作用户: chenyg,
     * 而非配置文件中定义"法外狂徒:张三",
     * 通过扩展 Environment 中的 PropertySource, 实现 user.name 优先级的验证
     */
    @Bean
    public User user(@Value("${myUser.id}") Long id,
                     @Value("${user.name}") String name
    ) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        // 扩展 Environment 中的 PropertySource
        // 添加 PropertySource 操作必须在 refresh 方法之前完成
        Map<String, Object> propertiesSource = new HashMap<>();
        propertiesSource.put("user.name", "zhangsan");
        org.springframework.core.env.PropertySource propertySource = new MapPropertySource("first-property-source", propertiesSource);
        context.getEnvironment().getPropertySources().addFirst(propertySource);

        // 注册当前类 作为 Configuration Class
        context.register(PropertySourceDemo.class);
        // 启动应用上下文
        context.refresh();
        // beanName  和 bean 映射
        Map<String, User> usersMap = context.getBeansOfType(User.class);
        for (Map.Entry<String, User> entry : usersMap.entrySet()) {
            System.out.printf("User Bean Name : %s , content: %s %n", entry.getKey(), entry.getValue());
        }
        System.out.println(context.getEnvironment().getPropertySources());
        //  关闭 Spring 应用上下文
        context.close();
    }
}
