package com.tongu.search.model.vo;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.Data;

@Data
public class PasswordVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1604853048076199515L;

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
	
	public PasswordVO(Long id, String projectType, String projectName, String userPassword, String remark) {
		this.id = id;
		this.projectType = projectType;
		this.projectName = projectName;
		this.remark = remark;
		this.userPassword = userPassword;
	}
}
