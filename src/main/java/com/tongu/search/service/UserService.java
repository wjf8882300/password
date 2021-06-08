package com.tongu.search.service;

import com.tongu.search.model.bo.UserBO;
import com.tongu.search.model.entity.UserEntity;

public interface UserService {

	/**
	 * 登陆
	 * @param userBO
	 * @return token
	 */
	String login(UserBO userBO);

	/**
	 * 校验并返回userId
	 * @param random
	 * @return
	 */
	UserEntity check(String random);

	/**
	 * 获取token
	 * @param userId
	 * @return
	 */
	String getToken(Long userId);

	/**
	 * 获取用户id
	 * @param token
	 * @return
	 */
	Long getUserId(String token);

	/**
	 * 保存用户凭证
	 * @param userName
	 * @param key
	 */
	void saveUserCredentials(String userName, String key);

	/**
	 * 获取用户凭证
	 * @param userName
	 * @return
	 */
	String getUserCredentials(String userName);

	/**
	 * 通过id查询名称
	 * @param userId
	 * @return
	 */
	String findUserNameById(Long userId);

	/**
	 * 判断是否可以访问
	 * @param url
	 * @param token
	 * @return
	 */
	Boolean canAccess(String url, String ip, String token);
}
