package org.cyg.thinking.in.spring.configuration.metadata;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.ObjectUtils;

/**
 * Bean 配置元信息 示例
 */
public class BeanConfigurationMetadataDemo {

    public static void main(String[] args) {
        System.out.println(BeanConfigurationMetadataDemo.class.getResource("/"));
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        builder.addPropertyValue("name", "cyg");
        // 获取 AbstractBeanDefinition
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        // 附加属性 (不影响Bean populate / initialize 实例化, 属性赋值, 初始化)
        beanDefinition.setAttribute("name", "法外狂徒:张三");
        // 当前 BeanDefinition 来自于何方 (辅助作用)
        beanDefinition.setSource(BeanConfigurationMetadataDemo.class); //
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (ObjectUtils.nullSafeEquals("user", beanName) && User.class.equals(bean.getClass())) {
                    // 属性(存储)上下文
                    BeanDefinition bd = beanFactory.getBeanDefinition(beanName);
                    if (BeanConfigurationMetadataDemo.class.equals(bd.getSource())) {
                        // 通过 source 判断来源
                        String name = (String) bd.getAttribute("name");
                        User user = (User) bean;
                        user.setName(name);
                    }

                }
                return bean;
            }
        });
        // 注册 User 的 BeanDefinition
        beanFactory.registerBeanDefinition("user", beanDefinition);

        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);
    }
}
