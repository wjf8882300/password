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
@Table(name = "cp_t_user")
public class UserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -318444171334675763L;
	
	@Id
	private Long id;

	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 密码
	 */
	private String userPassword;

	/**
	 * 凭证(google身份校验器)
	 */
	private String credential;

	/**
	 * 用户状态：0-无效/1-有效
	 */
	private String userStatus;
}
