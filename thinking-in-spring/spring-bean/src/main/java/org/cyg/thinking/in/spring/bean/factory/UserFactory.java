package org.cyg.thinking.in.spring.bean.factory;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;

/**
 * {@link User} 工厂类
 */
public interface UserFactory {

    default User createUser() {
        return User.createUser();
    }
}
