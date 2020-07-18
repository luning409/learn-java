package org.cyg.thinking.in.spring.ioc.overview.dependency.lookup;

import org.cyg.thinking.in.spring.ioc.overview.anntotation.Super;
import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 依赖查找实例
 * 1.通过名称来查找
 * 2.通过类型来查找
 */
public class DependencyLookupDemo {

    public static void main(String[] args) {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");
        // 通过类型查找
        lookupByType(beanFactory);
        // 按照类型查找集合对象
        lookupByCollectionType(beanFactory);
        // 通过注解查找
        lookupByAnnotationType(beanFactory);
        // 通过名称查找
//        lookupInRealTime(beanFactory);
//        lookupInLazy(beanFactory);
    }

    private static void lookupByAnnotationType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, Object> users = listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("查找标注 @Super 所有的集合对象:" + users);
        }
    }

    private static void lookupByCollectionType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到所有的 User 集合对象:" + users);
        }

    }

    private static void lookupByType(BeanFactory beanFactory) {
        User user =  beanFactory.getBean(User.class);
        System.out.println("实时查找" + user);
    }

    private static void lookupInLazy(BeanFactory beanFactory) {
        // 延时查找 objectFactory
        ObjectFactory<User> objectFactory = (ObjectFactory<User> ) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("延时查找" + user);
        User user1 = objectFactory.getObject();
        System.out.println(user == user1);
    }
    private static void lookupInRealTime(BeanFactory beanFactory){
        // 实时查找
        User user = beanFactory.getBean("user", User.class);
        System.out.println("实时查找" + user);
    }


}
