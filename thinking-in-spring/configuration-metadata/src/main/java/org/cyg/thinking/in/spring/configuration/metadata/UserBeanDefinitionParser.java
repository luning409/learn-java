package org.cyg.thinking.in.spring.configuration.metadata;

import org.cyg.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * "user" 元素的 {@link org.springframework.beans.factory.xml.BeanDefinitionParser} 实现<br/>
 * 需要设置 shouldGenerateId() 方法返回为 true, 这样当属性不存在 id  时, 会自动生成ID <br/>
 * 或者重写以下方法:
 * org.springframework.beans.factory.xml.AbstractBeanDefinitionParser#resolveId(org.w3c.dom.Element, org.springframework.beans.factory.support.AbstractBeanDefinition, org.springframework.beans.factory.xml.ParserContext)
 *
 */
public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return User.class;
    }

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        // 解析
        setPropertyValue("id", element, builder);
        setPropertyValue("name", element, builder);
        setPropertyValue("city", element, builder);
    }

    private void setPropertyValue(String attributeName, Element element, BeanDefinitionBuilder builder) {
        String attributeVale = element.getAttribute(attributeName);
        if (StringUtils.hasText(attributeVale)) {
            builder.addPropertyValue(attributeName, attributeVale); //-> <property name="attributeName" value="attributeVale"/>
        }
    }

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }

}
