package com.tongu.search.model.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class IncomeBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4477411379291421721L;

	/**
	 * 项目类型
	 */
	private String incomeType;

	/**
	 * 项目名称
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date startDate;
	
	/**
	 * 密码
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date endDate;

	private Long userId;

	private String id;
}
