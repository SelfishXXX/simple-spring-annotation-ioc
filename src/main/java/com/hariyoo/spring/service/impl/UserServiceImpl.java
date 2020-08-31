package com.hariyoo.spring.service.impl;

import com.hariyoo.spring.annotation.Autowired;
import com.hariyoo.spring.annotation.Component;
import com.hariyoo.spring.service.UserService;

/**
 * @author hariyoo
 * @Date 2020/8/22 14:27
 */
@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private GoodServiceImpl goodService;

	private String username = "张三";

	@Override
	public Long login(Long uid) {
		System.out.println(uid+":登陆成功");
		System.out.println("用户名为："+username);
		return uid;
	}

	@Override
	public void sell(Integer num) {
		goodService.sell(num);
	}

}
