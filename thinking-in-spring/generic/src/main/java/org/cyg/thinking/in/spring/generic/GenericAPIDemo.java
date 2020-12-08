package org.cyg.thinking.in.spring.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Java 泛型 API 示例
 */
public class GenericAPIDemo {

    public static void main(String[] args) {

        // 原生类型 primitive type int long float
        Class intClass = int.class;

        // 数组类型 array type int], Object[]
        Class objectArray = Object[].class;

        // 原始类型 raw type : java.lang.String
        Class rawClass = String.class;

        // 泛型参数类型 parameterized Type
        ParameterizedType parameterizedType = (ParameterizedType) ArrayList.class.getGenericSuperclass();
        // parameterizedType.getRawType() = java.util.AbstractList
//        System.out.println(parameterizedType.getRawType());

        System.out.println(parameterizedType.toString());

        // 泛型类型变量 Type Variable:
        Type[] typeVariables = parameterizedType.getActualTypeArguments();
        Stream.of(typeVariables)
                .map(TypeVariable.class::cast) // Type -> TypeVariable
                .forEach(System.out::println);

    }
}
