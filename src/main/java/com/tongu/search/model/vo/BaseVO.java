package com.tongu.search.model.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class BaseVO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5140713244415575899L;

	private Integer code;
	
	private String message;
	
	T data;
	
	public BaseVO() {
		
	}
	
	public BaseVO(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
}
