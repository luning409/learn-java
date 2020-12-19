package org.cyg.thinking.in.spring.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 注解驱动异步事件处理器示例
 */
@EnableAsync // 激活Spring 异步特效
public class AnnotatedAsyncEventHandlerDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        // 1. 注册当前类作为 Configuration Class
        context.register(AnnotatedAsyncEventHandlerDemo.class);
        // 同步监听器
        context.addApplicationListener(new MySpringEventListener());
        // 2. 启动 Spring 应用上下文
        context.refresh();
        // 3.发布自定义 Spring 事件
        context.publishEvent(new MySpringEvent("Hello, World!!"));
        // 4.关闭 Spring 应用上下文 (ContextClosedEvent)
        context.close();
    }

    @Async // 同步 -> 异步
    @EventListener(MySpringEvent.class)
    public void onEvent(MySpringEvent event) {
        System.out.printf("[线程 : %s] onEvent方法监听到事件 : %s\n", Thread.currentThread().getName(), event);
    }

    @Bean
    public Executor taskExecutor() {
        return Executors.newSingleThreadExecutor(new CustomizableThreadFactory("my-annotation-spring-event-thread-pool"));
    }
}
