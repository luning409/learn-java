package org.cyg.thinking.in.spring.bean.factory;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * {@link User} Bean 的 {@link FactoryBean} 实现
 */
public class UserFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

}
