package com.tongu.search.model.vo;

import java.io.Serializable;

import com.tongu.search.util.IDUtil;

import lombok.Data;

@Data
public class ProjectTypeVO implements Serializable{

	private String id;
	private String value;
	private String label;
	
	public ProjectTypeVO(String value) {
		this.value = value;
		this.id = IDUtil.nextStrId();
		this.label = value;
	}
}
