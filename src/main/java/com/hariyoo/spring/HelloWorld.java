package com.hariyoo.spring;

import cn.hutool.core.util.ClassUtil;
import com.hariyoo.spring.controller.UserController;
import com.hariyoo.spring.ioc.AutowireBeanFactory;
import com.hariyoo.spring.ioc.BeanFactory;
import com.hariyoo.spring.ioc.BeanProperty;
import com.hariyoo.spring.service.GoodsService;
import com.hariyoo.spring.service.UserService;
import com.hariyoo.spring.service.impl.GoodServiceImpl;
import com.hariyoo.spring.service.impl.UserServiceImpl;

import java.util.Map;
import java.util.Set;

/**
 * @author hariyoo
 * @Date 2020/8/22 13:52
 */
public class HelloWorld {


	public static void main(String[] args) {
		Set<Class<?>> classes = ClassUtil.scanPackage();

		BeanFactory factory = new AutowireBeanFactory();
		factory.initBeanMap(classes);
		Map<String, BeanProperty> beanMap = factory.showBeanMapInfo();
		beanMap.forEach((n,m) -> System.out.println(n+" => "+m));

		System.out.println("------------------------UserService-----------------------");
		UserService bean = factory.getBean(UserServiceImpl.class);
		bean.login(11L);
		bean.sell(100);

		System.out.println("------------------------GoodsService-----------------------");
		GoodsService goodsService = factory.getBean(GoodServiceImpl.class);
		goodsService.sell(200);
		goodsService.login(10L);

		System.out.println("------------------------Controller-----------------------");
		UserController bean1 = factory.getBean(UserController.class);
		bean1.doLogin(99L);

	}

}
