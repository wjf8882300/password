package com.tongu.search.model.entity;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


/**
 *  entity. @author Tools
 */
@Data
@Entity
@Table(name = "cp_t_passowrd")
public class PasswordEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -318444171334675763L;
	
	@Id
	private Long id;
	
	/**
	 * 用户id
	 */
	private Long userId;

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
}
