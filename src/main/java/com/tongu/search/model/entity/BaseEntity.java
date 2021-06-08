/**
 * 
 */
package com.tongu.search.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 基础实体模型
 * 
 *
 */
@SuppressWarnings("serial")
@MappedSuperclass
@Data
@ToString(callSuper = false)
@EqualsAndHashCode
public abstract class BaseEntity implements java.io.Serializable {
	
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Id
	@Column(length = 36)
	protected String id;

	/** 创建时间 */
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createDate;

	/** 备注 */
	@Column(length = 300)
	protected String remark;

	/** 更新时间 */
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastUpdateDate;

	/** 状态 */
	@Column
	protected String deleteStatus;
	
	/**
	 * @author HuangXiaodong 2015-04-17
	 * 设置记录基础信息
	 * 
	 * @param custId
	 *            操作人
	 * @param isInsert
	 *            是否插入
	 */
	public void setBasicModelProperty(Long custId, boolean isInsert) {
		if (isInsert) {
			this.setCreateDate(new Date());
		}
		this.setLastUpdateDate(new Date());
		this.setDeleteStatus("0");
	}

}
