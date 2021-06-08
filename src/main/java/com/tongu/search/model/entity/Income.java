package com.tongu.search.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "cp_t_income")
public class Income extends BaseEntity {

	@Column(name = "user_id")
	private Long userId;

	/**
	 * 收入类型
	 */
	@Column(name = "income_type")
	private Integer incomeType;

	/**
	 * 收入日期
	 */
	@Column(name = "income_date")
	@JSONField(format = "yyyy-MM-dd")
	private java.util.Date incomeDate;

	/**
	 * 发票金额
	 */
	private java.math.BigDecimal amount;
}
