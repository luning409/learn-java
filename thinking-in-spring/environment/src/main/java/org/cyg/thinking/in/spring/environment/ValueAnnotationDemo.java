package org.cyg.thinking.in.spring.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * {@link Value @Value} 注解示例
 */
public class ValueAnnotationDemo {
    @Value("${user.name}")
    private String userName;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册
        context.register(ValueAnnotationDemo.class);

        // 启动 Spring 应用上下文
        context.refresh();

        ValueAnnotationDemo demo = context.getBean(ValueAnnotationDemo.class);

        System.out.println(demo.userName); // "cyg"
        // 关闭 Spring 应用上下文
        context.close();
    }
}
