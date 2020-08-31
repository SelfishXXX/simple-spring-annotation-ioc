package com.hariyoo.spring.service.impl;

import com.hariyoo.spring.annotation.Autowired;
import com.hariyoo.spring.annotation.Component;
import com.hariyoo.spring.service.GoodsService;

/**
 * @author hariyoo
 * @Date 2020/8/23 12:19
 */
@Component
public class GoodServiceImpl implements GoodsService {

	@Autowired
	private UserServiceImpl userService;

	@Override
	public void sell(Integer num) {
		System.out.println("卖了"+num+"件货");
	}

	@Override
	public void login(Long uid) {
		userService.login(uid);
	}
}
