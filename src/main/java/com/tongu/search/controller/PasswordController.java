package com.tongu.search.controller;

import java.util.List;

import com.tongu.search.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tongu.search.model.RespData;
import com.tongu.search.model.bo.PasswordBO;
import com.tongu.search.model.vo.PasswordVO;
import com.tongu.search.model.vo.ProjectTypeVO;
import com.tongu.search.service.PasswordService;

@RestController
@RequestMapping("/password")
public class PasswordController extends BaseController {
	
	@Autowired
	private PasswordService passwordService;

	@PostMapping("/list")
	public RespData<List<PasswordVO>> list(@RequestBody PasswordBO passwordBO) {
		passwordBO.setUserId(WebUtil.getCurrentUserId());
		return success(passwordService.queryList(passwordBO));		
	}
	
	@PostMapping("/save")
	public RespData<Integer> save(@RequestBody PasswordBO passwordBO) {
		passwordBO.setUserId(WebUtil.getCurrentUserId());
		return success(passwordService.save(passwordBO));
	}
	
	@GetMapping("/query/projectType")
	public List<ProjectTypeVO> queryProjectType(String query) {
		return passwordService.queryProjectTypeList(query, WebUtil.getCurrentUserId());
	}

	@GetMapping("/query/one")
	public RespData<PasswordVO> queryOne(Long id) {
		return success(passwordService.queryOne(id));
	}

	@PostMapping("/delete")
	public RespData<Integer> delete(@RequestBody PasswordBO passwordBO) {
		return success(passwordService.delete(passwordBO.getId()));
	}
}
