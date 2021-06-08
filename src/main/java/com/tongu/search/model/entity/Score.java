package com.tongu.search.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "cp_t_score")
public class Score extends BaseEntity {

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "score_type_id")
	private String scoreTypeId;

	/**
	 * 收入类型
	 */
	@Column(name = "score_type")
	private String scoreType;

	/**
	 * 收入日期
	 */
	@Column(name = "score_date")
	@JSONField(format = "yyyy-MM-dd")
	private java.util.Date scoreDate;

	/**
	 * 发票金额
	 */
	@Column(name = "score_value")
	private Integer scoreValue;
}
