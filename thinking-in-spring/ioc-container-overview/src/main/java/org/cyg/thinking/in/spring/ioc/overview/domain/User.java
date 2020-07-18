package org.cyg.thinking.in.spring.ioc.overview.domain;

/**
 * 用户类
 */
public class User {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void init() {
        System.out.println("自定义初始化方法 init() : User 初始化中...");
    }

    public static User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        return user;
    }
}
