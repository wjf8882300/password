package com.tongu.search.model.bo;

import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4477411379291421721L;

	/**
	 * 用户名
	 */
	@NotEmpty(message = "用户名不能为空")
	private String userName;
	
	/**
	 * 密码
	 */
	@NotEmpty(message = "密码不能为空")
	private String userPassword;
}
