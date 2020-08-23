package com.hariyoo.spring.ioc;

import java.util.Map;
import java.util.Set;

/**
 * @author hariyoo
 * @Date 2020/8/22 19:12
 */
public interface BeanFactory {

	Map<String, BeanProperty> showBeanMapInfo();

	<T> T getBean(Class<T> clazz);

	void initBeanMap(Set<Class<?>> classes);
}
