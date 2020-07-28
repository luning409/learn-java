package org.cyg.thinking.in.spring.ioc.bean.scope;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.TimeUnit;

/**
 * 自定义 Scope {@link ThreadLocalScope} 示例
 */
public class ThreadLocalScopeDemo  {

    private static int times;

    @Bean
    @Scope(ThreadLocalScope.SCOPE_NAME)
    public User user() {
        return createUser();
    }

    private static User createUser() {
        User user = new User();
        user.setId(System.currentTimeMillis() + (++times));
        return user;
    }

    public static void main(String[] args)  {
        // 创建 AnnotationConfigApplicationContext 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 作为配置类 （Configuration Class）
        // 注册 Configuration Class （配置类）
        applicationContext.register(ThreadLocalScopeDemo.class);

        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            // 注册自定义 Scope
            beanFactory.registerScope(ThreadLocalScope.SCOPE_NAME, new ThreadLocalScope());
        });
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        scopeBeansByLookup(applicationContext);
        // 关闭 Spring 应用上下文
        applicationContext.close();
    }

    private static void scopeBeansByLookup(AnnotationConfigApplicationContext applicationContext) {
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread( () -> {
                User user = applicationContext.getBean("user", User.class);
                System.out.printf("[Thread id : %d]user = %s%n", Thread.currentThread().getId(), user);
            });
            // 启动线程
            thread.start();
            // 强制线程完成
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
