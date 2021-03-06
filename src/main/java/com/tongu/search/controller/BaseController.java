package com.tongu.search.controller;

import com.tongu.search.model.RespData;

public class BaseController {
	
	/** 默认成功，无数据 */
	public final <T> RespData<T> success() {
		return callback(200, "success", null);
	}

	/** 默认成功，有数据 */
	public final <T> RespData<T> success(T data) {
		return callback(200, "success", data);
	}

	/** 默认失败，系统错误 */
	public final <T> RespData<T> failure() {
		return callback(500, "抱歉，您的操作出问题了，请反馈客服处理，谢谢", null);
	}
	
	/** 默认失败，系统错误 */
	public final <T> RespData<T> failure(String message) {
		return callback(500, message, null);
	}

	/** 自定义失败错误 */
	public final <T> RespData<T> failure(T data) {
		return callback(500, "抱歉，您的操作出问题了，请反馈客服处理，谢谢", data);
	}
	
	/** 公共响应,有返回值 */
	private <T> RespData<T> callback(Integer code, String message, T data) {
		return new RespData<T>(code, message, data);
	}
}
