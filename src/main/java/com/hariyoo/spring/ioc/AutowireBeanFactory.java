package com.hariyoo.spring.ioc;

import com.hariyoo.spring.annotation.Autowired;
import com.hariyoo.spring.annotation.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author hariyoo
 * @Date 2020/8/22 19:14
 */
public class AutowireBeanFactory implements BeanFactory {

	private final Map<String, BeanProperty> beanMap = new HashMap<>();

	private final List<String> beanList = new ArrayList<>();

	public Map<String, BeanProperty> getBeanMap() {
		return beanMap;
	}

	@Override
	public Map<String, BeanProperty> showBeanMapInfo() {
		return getBeanMap();
	}


	@Override
	public <T> T getBean(Class<T> clazz) {
		String beanName = clazz.getName();
		BeanProperty beanProperty = beanMap.get(beanName);
		T bean = (T) beanProperty.getBean();
		return bean;
	}


	/**
	 * 初始化 ioc 容器
	 * @param classes
	 */
	@Override
	public void initBeanMap(Set<Class<?>> classes) {
		for (Class<?> clazz : classes) {
			String className = clazz.getName();
			Annotation[] annotations = clazz.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof Component) {
					putBeanMap(className);
					beanList.add(className);
					break;
				}
			}
		}

//		beanList.forEach(n -> injectValue(n));
		beanList.forEach(n -> injectValue2(n));

	}

	private void putBeanMap(String beanName) {
		BeanProperty beanProperty = new BeanProperty();
		try {
			Class<?> clazz = Class.forName(beanName);
			String simpleName = clazz.getSimpleName();
			Object bean = clazz.newInstance();

			beanProperty.setFullName(beanName);
			beanProperty.setClazz(clazz);
			beanProperty.setSimpleName(simpleName);
			beanProperty.setBean(bean);
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
		this.beanMap.put(beanName, beanProperty);
	}


	/**
	 * 给添加了@Autowired 的属性注入值
	 * 本质上这个方法就是递归赋值
	 * @param beanName
	 * @return
	 */
	private Object injectValue(String beanName) {
		// TODO: 2020/8/23 循环依赖怎么解决呢？
		BeanProperty beanProperty = getBeanProperty(beanName);
		Class<?> clazz = beanProperty.getClazz();
		Object bean = beanProperty.getBean();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field declaredField : declaredFields) {
			boolean isAutowiredPresent = declaredField.isAnnotationPresent(Autowired.class);
			if (!isAutowiredPresent) {
				continue;
			}
			try {
				declaredField.setAccessible(true);
				Object fieldValue = declaredField.get(bean);
				Class<?> fieldType = declaredField.getType();
				if (fieldValue == null) {
					Object o = injectValue(fieldType.getName());
					declaredField.set(bean, o);
				} else {
					declaredField.set(bean, fieldValue);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		return bean;
	}

	private BeanProperty getBeanProperty(String beanName) {
		BeanProperty beanProperty = beanMap.get(beanName);

		/**
		 * 如果没有根据类名称找到实例，那就看beanMap里面有没有想要获取的实现类
		 */
		if (beanProperty == null) {
			List<BeanProperty> beanPropertyTemps = new ArrayList<>();
			for (Map.Entry<String, BeanProperty> entry : beanMap.entrySet()) {
				BeanProperty entryValue = entry.getValue();
				try {
					if (Class.forName(beanName).isInstance(entryValue.getBean())) {
						beanPropertyTemps.add(entryValue);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			if (beanPropertyTemps.isEmpty()) {
				throw new RuntimeException(beanName + "没有注入");
			} else if (beanPropertyTemps.size() > 1) {
				throw new RuntimeException(beanName + "不清楚用哪个");
			} else {
				beanProperty = beanPropertyTemps.get(0);
			}
		}
		return beanProperty;
	}

	private void injectValue2(String beanName) {
		BeanProperty beanProperty = getBeanProperty(beanName);
		Object bean = beanProperty.getBean();
		Class<?> clazz = beanProperty.getClazz();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field declaredField : declaredFields) {

			boolean isAutowiredPresent = declaredField.isAnnotationPresent(Autowired.class);
			if (!isAutowiredPresent) {
				continue;
			}
			try {
				declaredField.setAccessible(true);
				Class<?> fieldType = declaredField.getType();
				BeanProperty fieldBeanProperty = getBeanProperty(fieldType.getName());
				declaredField.set(bean, fieldBeanProperty.getBean());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
	}

}
