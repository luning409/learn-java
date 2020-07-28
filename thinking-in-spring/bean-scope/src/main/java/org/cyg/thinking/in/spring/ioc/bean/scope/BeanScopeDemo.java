package org.cyg.thinking.in.spring.ioc.bean.scope;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Bean 作用域示例
 * "prototype" Bean 作用域
 * • 注意事项
 * • Spring 容器没有办法管理 prototype Bean 的完整生命周期，也没有办法记录示例的存
 * 在。销毁回调方法将不会执行，可以利用 BeanPostProcessor 进行清扫工作。
 */
public class BeanScopeDemo implements DisposableBean {

    private static int times = 0;

    // 默认 scope 就是 singleton
    @Bean
    public static User singletonUser() {
        return createUser();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static User prototypeUser() {
        return createUser();
    }

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser;

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser1;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser1;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser2;

    @Autowired
    private Map<String, User> users;

    @Autowired
    private ConfigurableListableBeanFactory beanFactory; // Resolveable Dependency

    private static User createUser() {
        User user = new User();
        user.setId(System.currentTimeMillis() + (++times));
        return user;
    }

    public static void main(String[] args) throws InterruptedException {
        // 创建 AnnotationConfigApplicationContext 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 作为配置类 （Configuration Class）
        // 注册 Configuration Class （配置类）
        applicationContext.register(BeanScopeDemo.class);

        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
                @Override
                public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                    System.out.printf("%s Bean 名称:%s 在初始化后回调...%n", bean.getClass().getName(), beanName);
                    return bean;
                }
            });
        });
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 结论一：
        // Singleton Bean 无论依赖查找还是依赖注入，均为一个对象
        // Prototype Bean 无论依赖查找还是依赖注入，均为新生成的对象
        // 结论二：
        // 如果依赖注入集合类型的对象, Singleton Bean 和 Prototype Bean 均会存在一个
        // Prototype Bean 有别于其他地方的依赖注入 Prototype Bean
        // 结论三：
        // 无论是 Singleton Bean 还是 Prototype Bean 均会执行初始化方法回调
        // 不过仅仅 Singleton Bean 会执行销毁方法回调
        scopeBeansByLookup(applicationContext);

        scopeBeansByInjection(applicationContext);
        System.out.println("times = " + times);
        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }

    private static void scopeBeansByInjection(AnnotationConfigApplicationContext applicationContext) {
        BeanScopeDemo beanScopeDemo = applicationContext.getBean(BeanScopeDemo.class);
        System.out.println("beanScopeDemo.singletonUser = " + beanScopeDemo.singletonUser);
        System.out.println("beanScopeDemo.singletonUser1 = " + beanScopeDemo.singletonUser1);
        System.out.println("beanScopeDemo.prototypeUser = " + beanScopeDemo.prototypeUser);
        System.out.println("beanScopeDemo.prototypeUser1 = " + beanScopeDemo.prototypeUser1);
        System.out.println("beanScopeDemo.prototypeUser2 = " + beanScopeDemo.prototypeUser2);
        System.out.println("beanScopeDemo.users = " + beanScopeDemo.users);
    }

    private static void scopeBeansByLookup(AnnotationConfigApplicationContext applicationContext) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            // singletonUser 是共享 Bean 对象
            User singletonUser = applicationContext.getBean("singletonUser", User.class);
            System.out.println("singletonUser = " + singletonUser);
            // prototypeUser 是每次依赖查找均生成新的对象
            User prototypeUser = applicationContext.getBean("prototypeUser", User.class);
            // 输出太快了...
            TimeUnit.MILLISECONDS.sleep(3L);
            System.out.println("prototypeUser = " + prototypeUser);
        }
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("当前 BeanScopeDemo 正在销毁中");
        this.prototypeUser.destroy();
        this.prototypeUser1.destroy();
        this.prototypeUser2.destroy();
        // 获取 BeanDefinition
        users.forEach((beanName, user) -> {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            if (beanDefinition.isPrototype()) {
                // 如果当前 Bean 是 Prototype scope
                user.destroy();
            }
        });
        System.out.println("当前 BeanScopeDemo 销毁完成");
    }
}
