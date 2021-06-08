package com.tongu.search.service;

import java.util.List;

import com.tongu.search.model.bo.PasswordBO;
import com.tongu.search.model.vo.PasswordVO;
import com.tongu.search.model.vo.ProjectTypeVO;

public interface PasswordService {

	Integer save(PasswordBO passwordBO);
	
	List<PasswordVO> queryList(PasswordBO passwordBO);
	
	List<ProjectTypeVO> queryProjectTypeList(String projectType, Long userId);

	PasswordVO queryOne(Long id);

	Integer delete(Long id);
}
