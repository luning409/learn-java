package org.cyg.thinking.in.spring.application.context.lifecycle;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.Lifecycle;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;

/**
 * Spring Shutdown Hook  Bean 示例
 */
public class SpringShutdownHookDemo {

    public static void main(String[] args) throws IOException {
        GenericApplicationContext context = new GenericApplicationContext();

        // 注册 MyLifecycle 成为一个 Bean
        context.registerBeanDefinition("myLifecycle",
                BeanDefinitionBuilder.rootBeanDefinition(MyLifecycle.class).getBeanDefinition());

        context.addApplicationListener(new ApplicationListener<ContextClosedEvent>() {
            @Override
            public void onApplicationEvent(ContextClosedEvent event) {
                System.out.printf("[线程 %s] ContextClosedEvent 处理\n", Thread.currentThread().getName());
            }
        });

        // 刷新 Spring 应用上下文
        context.refresh();
        context.registerShutdownHook();
        //
        System.out.println("按任意键继续并关闭 Spring 应用上下文");
        System.in.read();

        // 关闭 Spring 应用(同步)
        context.close();
        System.in.read();
    }
}
