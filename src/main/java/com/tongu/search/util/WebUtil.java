package com.tongu.search.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tongu.search.exception.RbacErrorCodeEnum;
import com.tongu.search.exception.RbacException;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tongu.search.model.RespData;

/**
 * Web层辅助工具类
 * @author zhimou.qu
 * @create 2018年7月12日
 */
public class WebUtil {

	/** 获取当前ServletRequestAttributes */
	public static ServletRequestAttributes getCurServletRequestAttributes() {
		return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	}

	/** 获取当前HttpServletRequest */
	public static HttpServletRequest getCurRequest() {
		return getCurServletRequestAttributes().getRequest();
	}

	/** 获取当前HttpServletResponse */
	public static HttpServletResponse getCurResponse() {
		return getCurServletRequestAttributes().getResponse();
	}

	/** 公共响应,有返回值 */
	public static <T> RespData<T> callback(Integer code, String message, T data) {
		return new RespData<T>(code, message, data);
	}

	/** 获取客户端IP */
	public static final String getHost(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if ("127.0.0.1".equals(ip)) {
			InetAddress inet = null;
			try {
				inet = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			ip = inet.getHostAddress();
		}
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}

	/**
	 * 获取路径
	 *
	 * @author  wangjf
	 * @date    2019年1月24日 下午5:20:22
	 * @param request
	 * @return
	 */
	public static final String getRequestPath(HttpServletRequest request) {
		return request.getServletPath();
	}

	/**
	 * 获取用户id
	 * @return
	 */
	public static Long getCurrentUserId() {
		Long userId = (Long)getCurServletRequestAttributes().getRequest().getAttribute("userId");
		if(userId == null) {
			throw new RbacException(RbacErrorCodeEnum.AUTH_FAILURE);
		}
		return userId;
	}
}
