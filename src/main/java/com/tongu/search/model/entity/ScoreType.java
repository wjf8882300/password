package com.tongu.search.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "cp_t_score_type")
public class ScoreType extends BaseEntity {



	/**
	 * 收入类型
	 */
	@Column(name = "score_type")
	private String scoreType;

	/**
	 * 发票金额
	 */
	@Column(name = "score_value")
	private Integer scoreValue;
}
