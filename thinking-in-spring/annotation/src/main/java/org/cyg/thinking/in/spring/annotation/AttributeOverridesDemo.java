package org.cyg.thinking.in.spring.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring 注解属性覆盖示例
 *
 */
@MyComponentScan2(packages = "org.cyg.thinking.in.spring.annotation")
// @MyComponentScan2.scanBasePackages <- @MyComponentScan.scanBasePackages 隐形覆盖
public class AttributeOverridesDemo {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册
        context.register(AttributeOverridesDemo.class);
        // 启动 Spring 应用上下文
        context.refresh();

        // 依赖查找 TestClass Bean
        // TestClass 标注 @MyComponent2
        // @MyComponent2 <- @MyComponent <- @Component
        // 从 Spring 开始支持多层次 @Component “派生”
        TestClass testClass = context.getBean(TestClass.class);
        System.out.println(testClass);
        // Annotation -> AnnotationAttributes(Map)
        // 关闭 Spring 应用上下文
        context.close();
    }
}
