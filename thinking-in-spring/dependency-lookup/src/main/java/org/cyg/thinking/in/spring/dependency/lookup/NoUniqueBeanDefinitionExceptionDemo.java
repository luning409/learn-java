package org.cyg.thinking.in.spring.dependency.lookup;

import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * {@link NoUniqueBeanDefinitionException} 示例代码
 */
public class NoUniqueBeanDefinitionExceptionDemo {

    public static void main(String[] args) {
        // 创建 AnnotationConfigApplicationContext 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 NoUniqueBeanDefinitionExceptionDemo 作为配置类 （Configuration Class）
        applicationContext.register(NoUniqueBeanDefinitionExceptionDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
        try {
            // 由于 Spring 应用上下文存在两个 String 类型的 Bean， 通过单一类型查找 会抛出异常
            applicationContext.getBean(String.class);
        } catch (NoUniqueBeanDefinitionException e) {
            System.err.printf("当前Spring 应用上下文 %d 个 %s 类型 Bean ，具体原因: %s%n",
                    e.getNumberOfBeansFound(), String.class.getName(), e.getMessage());
        }

        // 关闭应用上下文
        applicationContext.close();
    }

    @Bean
    @Lazy
    public String bean1() {
        return "bean1";
    }

    @Bean
    public String bean2() {
        return "bean2";
    }

    @Bean
    @Lazy
    public String bean3() {
        return "bean3";
    }
}
