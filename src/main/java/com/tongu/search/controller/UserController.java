package com.tongu.search.controller;

import com.tongu.search.model.RespData;
import com.tongu.search.model.bo.AuthBO;
import com.tongu.search.model.bo.UserBO;
import com.tongu.search.model.entity.UserEntity;
import com.tongu.search.service.UserService;
import com.tongu.search.util.GoogleAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public RespData<String> list(@Valid @RequestBody UserBO userBO) {
		return success(userService.login(userBO));
	}

	@GetMapping("/qr")
	public RespData<String> createQr(Long id) {
		UserEntity user = userService.check(id);
		String userName = user == null ? null : user.getUserName();
		if(!StringUtils.isEmpty(userName)){
			return success(GoogleAuthUtil.getQr(userName));
		}
		return failure("生成校验码失败");
	}

	@PostMapping("/check")
	public RespData<String> check(@Valid @RequestBody AuthBO authBO) {
		UserEntity userEntity = userService.check(authBO.getId());
		if(userEntity != null && GoogleAuthUtil.check(userEntity.getUserName(), authBO.getCode())) {
			return success(userService.getToken(userEntity.getId()));
		}
		return failure("验证失败");
	}
}
