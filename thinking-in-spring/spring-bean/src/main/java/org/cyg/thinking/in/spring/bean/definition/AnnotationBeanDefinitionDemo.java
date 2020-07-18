package org.cyg.thinking.in.spring.bean.definition;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 注解 BeanDefinition 示例
 */
// 3.  通过 @Import 来进行导入
@Import(AnnotationBeanDefinitionDemo.Config.class)
public class AnnotationBeanDefinitionDemo {

    public static void main(String[] args) {
        // 创建 AnnotationConfigApplicationContext 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 作为配置类 （Configuration Class）
        // 注册 Configuration Class （配置类）
        applicationContext.register(AnnotationBeanDefinitionDemo.class);

        // 通过 BeanDefinition 注册 API 实现
        // 1.命名 Bean 的注册方式
        registerUserBeanDefinition(applicationContext, "lisi-user");
        // 2. 非命名 Bean 的注册方式
        registerUserBeanDefinition(applicationContext);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 按照类型依赖查找
        Map<String, Config> configBeans = applicationContext.getBeansOfType(Config.class);
        System.out.println("Config 类型的所有 Bean" + configBeans);
        System.out.println("User 类型的所有 Bean" + applicationContext.getBeansOfType(User.class));

        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }

    /**
     * 命名 Bean 的注册方式
     * @param registry
     * @param beanName
     */
    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        builder
                .addPropertyValue("id", 1)
                .addPropertyValue("name", "李四");
        // 判断如果 beanName 参数存在时
        if (StringUtils.hasText(beanName)) {
            // 注册 BeanDefinition
            registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
        } else {
            // 非命名的方式
            BeanDefinitionReaderUtils.registerWithGeneratedName(builder.getBeanDefinition(), registry);
        }

    }

    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry) {
        registerUserBeanDefinition(registry, null);
    }

    // 2.  通过 @Component 方式
    // 定义当前类作为 Spring Bean (组件)
    @Component
    public static class Config {
        // 1.  通过 @Bean 方式定义

        /**
         * 通过 Java 注解的方式，定义了一个 Bean
         */
        @Bean(name = {"user", "zhangsan-user"})
        public User user() {
            User user = new User();
            user.setId(1L);
            user.setName("张三");
            return user;
        }
    }


}
