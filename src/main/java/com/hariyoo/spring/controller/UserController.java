package com.hariyoo.spring.controller;

import com.hariyoo.spring.annotation.Autowired;
import com.hariyoo.spring.annotation.Component;
import com.hariyoo.spring.service.UserService;

/**
 * @author hariyoo
 * @Date 2020/8/22 14:32
 */
@Component
public class UserController {

	@Autowired
	private UserService userService;

	public Long doLogin(Long uid) {
		return userService.login(uid);
	}

}
