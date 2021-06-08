package com.tongu.search.model.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ScoreBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4477411379291421721L;

	/**
	 * 积分类型
	 */
	private String scoreType;

	private String id;

	private String scoreTypeId;

	/**
	 * 积分名称
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date scoreDate;
}
