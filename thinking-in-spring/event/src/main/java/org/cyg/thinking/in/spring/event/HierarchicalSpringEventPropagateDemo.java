package org.cyg.thinking.in.spring.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 层次性 Spring 事件传播实例
 */
public class HierarchicalSpringEventPropagateDemo {

    public static void main(String[] args) {
        // 1. 创建 parent Spring 应用上下文
        AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
        parentContext.setId("parent-context");
        // 注册 MyListener 到 parent Spring 应用上下文
        parentContext.register(MyListener.class);

        // 2. 创建 current Spring 应用上下文
        AnnotationConfigApplicationContext currentContext = new AnnotationConfigApplicationContext();
        currentContext.setId("current-content");

        // 3. current -> parent
        currentContext.setParent(parentContext);
        // 注册 MyListener 到 parent Spring 应用上下文
        currentContext.register(MyListener.class);
        // 4. 启动 parent 应用上下文
        parentContext.refresh();
        // 5. 启动 current 应用上下文
        currentContext.refresh();

        // 关闭所有 Spring 应用上下文
        currentContext.close();
        parentContext.close();


    }

    static class MyListener implements ApplicationListener<ApplicationContextEvent> {

        private static Set<ApplicationContextEvent> processed = new LinkedHashSet<>();

        @Override
        public void onApplicationEvent(ApplicationContextEvent event) {
            // 存在两个 实例 MyListener
            if (processed.add(event)) {
                System.out.printf("监听到 Spring 应用上下文 [ ID : %s ] 的 事件 :%s \n", event.getApplicationContext().getId(),
                        event.getClass().getSimpleName());
            }

        }
    }
}
