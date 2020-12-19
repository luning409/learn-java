package org.cyg.thinking.in.spring.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 自定义 {@link Component} Scan
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MyComponentScan
public @interface MyComponentScan2 {
    /**
     * 隐形别名: “多态”，子注解提供想新的属性方法引用“父” （元）注解中的属性方法
     * @return
     */
    @AliasFor(annotation = MyComponentScan.class, attribute = "scanBasePackages") // 隐形别名
    String[] basePackages() default {};
    // @MyComponentScan2.basePackages
    // -> @MyComponentScan.scanBasePackages
    // -> @AliasFor @ComponentScan.basePackages
    // -> @AliasFor @ComponentScan.value (显性别名)
    // -> @AliasFor @ComponentScan.value // 传递隐形别名
    /**
     * 与元注解 MyComponentScan 同名属性
     * @return
     */
    String[] scanBasePackages() default {"#"};

    @AliasFor("scanBasePackages")
    String[] packages() default {"#"}; // packages 覆盖了 scanBasePackages 覆盖了 元注解 scanBasePackages

}
