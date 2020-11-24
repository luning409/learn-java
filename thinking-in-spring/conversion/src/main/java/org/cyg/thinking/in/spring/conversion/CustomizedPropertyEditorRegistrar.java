package org.cyg.thinking.in.spring.conversion;


import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

import java.util.Properties;

/**
 * 自定义 {@link PropertyEditorRegistrar} 实现
 *
 */
// @Component // 3. 将声明为 Spring Bean
public class CustomizedPropertyEditorRegistrar implements PropertyEditorRegistrar {

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        // 1. 通用类型转换 正确写法
//        registry.registerCustomEditor(Properties.class, new StringToPropertiesPropertyEditor());
        // 2. Java Bean 属性类型状态 第二个方式会失败 因为 java.util.Map 类型 会导致多次 进入到 StringToPropertiesPropertyEditor#setAsText
//        registry.registerCustomEditor(User.class, "context", new StringToPropertiesPropertyEditor());
        registry.registerCustomEditor(Properties.class, "context", new StringToPropertiesPropertyEditor());
    }
}
