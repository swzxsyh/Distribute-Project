package com.distribute.persistence.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;


/**
 * Spring管理Bean与Value获取
 *
 * @author swzxsyh
 */
@Slf4j
public class SpringUtils implements ApplicationContextAware, EmbeddedValueResolverAware {

    private static ApplicationContext applicationContext;

    private static StringValueResolver stringValueResolver;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clz) throws BeansException {
        return (T) applicationContext.getBean(clz);
    }


    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        SpringUtils.stringValueResolver = resolver;
    }

    public static String getValue(String name) {
        try {

            name = "${" + name + "}";
            String value = stringValueResolver.resolveStringValue(name);
            if (!StringUtils.isEmpty(value)) {
                return value;
            }
        } catch (Exception e) {
            log.error("getValue err:", e);
        }
        return null;
    }

    public static String getValue(String name, String def) {
        try {
            name = "${" + name + "}";
            String value = stringValueResolver.resolveStringValue(name);
            if (!StringUtils.isEmpty(value)) {
                return value;
            }
        } catch (Exception e) {
            log.error("getValue err:", e);
        }
        return def;
    }
}
