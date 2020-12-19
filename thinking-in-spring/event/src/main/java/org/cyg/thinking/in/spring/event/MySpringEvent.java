package org.cyg.thinking.in.spring.event;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义 Spring 事件
 */
public class MySpringEvent extends ApplicationEvent {

    public MySpringEvent(String message) {
        super(String.format("[线程 : %s] ： %s", Thread.currentThread().getName(), message));
    }

    @Override
    public String getSource() {
        return (String) super.getSource();
    }

    public String getMessage() {
        return getSource();
    }
}
