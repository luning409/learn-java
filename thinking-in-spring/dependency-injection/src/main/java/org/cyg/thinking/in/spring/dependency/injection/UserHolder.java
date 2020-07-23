package org.cyg.thinking.in.spring.dependency.injection;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;

/**
 * {@link User} 的 holder 对象
 */
public class UserHolder {

    private User user;

    public UserHolder() {
    }

    public UserHolder(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                '}';
    }
}
