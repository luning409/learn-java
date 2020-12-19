package org.cyg.thinking.in.spring.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Enable 模块驱动示例
 */
@EnableHelloWorld
public class EnableModuleDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册
        context.register(EnableModuleDemo.class);
        // 启动 Spring 应用上下文
        context.refresh();

        // 依赖查找 TestClass Bean
        String str = context.getBean("helloWorld", String.class);
        System.out.println(str);
        // 关闭 Spring 应用上下文
        context.close();
    }
}
