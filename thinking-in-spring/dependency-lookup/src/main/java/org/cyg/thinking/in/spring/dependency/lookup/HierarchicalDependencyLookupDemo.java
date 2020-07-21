package org.cyg.thinking.in.spring.dependency.lookup;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 层次性依赖查找 示例
 */
public class HierarchicalDependencyLookupDemo {

    public static void main(String[] args) {
        // 创建 AnnotationConfigApplicationContext 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 AnnotationApplicationContextAsIoCContainerDemo 作为配置类 （Configuration Class）
        applicationContext.register(HierarchicalDependencyLookupDemo.class);
        // 1. 获取 HierarchicalBeanFactory <- ConfigurableBeanFactory <- ConfigurableListableBeanFactory
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        System.out.println("当前 BeanFactory 的 Parent BeanFactory :" + beanFactory.getParentBeanFactory());
        // 2. 设置 Parent BeanFactory
        ConfigurableListableBeanFactory parentFactory = createParentBeanFactory();
        beanFactory.setParentBeanFactory(parentFactory);
        System.out.println("当前 BeanFactory 的 Parent BeanFactory :" + beanFactory.getParentBeanFactory());
        // 启动应用上下文
        applicationContext.refresh();
        displayContainsLocalBean(beanFactory, "user");
        displayContainsLocalBean(parentFactory, "user");
        displayContainsBean(beanFactory, "user");
        displayContainsBean(parentFactory, "user");
        // 关闭应用上下文
        applicationContext.close();
    }

    private static void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.println(String.format("当前 BeanFactory[%s] 是否包含 Bean [name : %s] : %s", beanFactory, beanName,
                containsBean(beanFactory, beanName)));
    }

    private static boolean containsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
        if (parentBeanFactory instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory parentHierarchicalBeanFactory = (HierarchicalBeanFactory) parentBeanFactory;
            if (containsBean(parentHierarchicalBeanFactory, beanName)) {
                return true;
            }
        }
        return beanFactory.containsLocalBean(beanName);
    }

    private static void displayContainsLocalBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.println(String.format("当前 BeanFactory[%s] 是否包含 Bean [name : %s] : %s", beanFactory, beanName,
                beanFactory.containsLocalBean(beanName)));
    }

    private static ConfigurableListableBeanFactory createParentBeanFactory() {
        // 创建 BeanFactory 容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // XML 配置文件 ClassPath 路径
        String location = "classpath:/META-INF/dependency-lookup-context.xml";
        // 加载配置
        int beanDefinitionsCount = reader.loadBeanDefinitions(location);
        System.out.println("Bean 定义加载数量:" + beanDefinitionsCount);
        return beanFactory;
    }
}
