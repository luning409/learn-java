package org.cyg.thinking.in.spring.configuration.metadata;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.cyg.thinking.in.spring.ioc.overview.enums.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/**
 * 基于 Java 注解 资源的 YAML 外部化配置 示例
 */
@PropertySource(
        name = "yamlPropertySource",
        value = "classpath:/META-INF/user.yml",
        factory = YamlPropertySourceFactory.class
        )
public class AnnotatedYamlPropertySourceDemo {
    /***
     * user.name 是 Java Properties 默认存在, 当前操作用户: chenyg,
     * 而非配置文件中定义"zhangsan",
     */
    @Bean
    public User user(@Value("${user.id}") Long id,
                     @Value("${user.name}") String name,
                     @Value("${user.city}") City city
    ) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setCity(city);
        return user;
    }
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册当前类 作为 Configuration Class
        context.register(AnnotatedYamlPropertySourceDemo.class);
        // 启动应用上下文
        context.refresh();
        // 获取 Map YAML 对象
        User user = context.getBean("user", User.class);
        System.out.println(user);
        context.close();
    }
}
