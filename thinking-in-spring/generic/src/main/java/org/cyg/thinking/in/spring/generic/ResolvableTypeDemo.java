package org.cyg.thinking.in.spring.generic;

import org.springframework.core.ResolvableType;

/**
 * {@link ResolvableType} 示例
 */
public class ResolvableTypeDemo {

    public static void main(String[] args) {
        // 工厂创建
        // StringList <- ArrayList <- AbstractList <- List <- Collection
        ResolvableType resolvableType = ResolvableType.forClass(StringList.class);

        System.out.println(resolvableType.getSuperType()); // ArrayList
        System.out.println(resolvableType.getSuperType().getSuperType()); // AbstractList

        System.out.println(resolvableType.asCollection().resolve()); // 获取 Raw Type
        System.out.println(resolvableType.asCollection().resolveGeneric(0)); // 获取泛型参数类型
    }
}
