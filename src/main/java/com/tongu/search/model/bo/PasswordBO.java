package com.tongu.search.model.bo;

import java.io.Serializable;

import lombok.Data;

@Data
public class PasswordBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4477411379291421721L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 项目类型
	 */
	private String projectType;

	/**
	 * 项目名称
	 */
	private String projectName;
	
	/**
	 * 密码
	 */
	private String userPassword;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * token
	 */
	private Long userId;
}
