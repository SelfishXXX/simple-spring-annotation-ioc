package com.hariyoo.spring.ioc;

/**
 * @author hariyoo
 * @Date 2020/8/22 19:54
 */
public class BeanProperty {

	private Class clazz;

	private String fullName;

	private Object bean;

	private String simpleName;

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	@Override
	public String toString() {
		return "BeanProperty{" +
				"clazz=" + clazz +
				", fullName='" + fullName + '\'' +
				", bean=" + bean +
				", simpleName='" + simpleName + '\'' +
				'}';
	}
}
