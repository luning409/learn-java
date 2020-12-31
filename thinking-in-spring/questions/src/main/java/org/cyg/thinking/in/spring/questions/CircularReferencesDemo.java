package org.cyg.thinking.in.spring.questions;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * {@link BeanFactory} 循环引用 （依赖） 示例
 */
public class CircularReferencesDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(CircularReferencesDemo.class);
        // 不允许循环依赖，存在循环依赖，则抛出异常信息如：  currently in creation: Is there an unresolvable circular reference?
        // 默认值为 true
        context.setAllowCircularReferences(false);
        context.refresh();

        System.out.println("Student : " + context.getBean(Student.class));
        System.out.println("ClassRoom : " + context.getBean(ClassRoom.class));

        context.close();

    }

    @Bean
    public static Student student() {
        Student student = new Student();
        student.setId(1L);
        student.setName("张三");
        return student;
    }

    @Bean
    public static ClassRoom classRoom() {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setName("C122");
        return classRoom;
    }
}
