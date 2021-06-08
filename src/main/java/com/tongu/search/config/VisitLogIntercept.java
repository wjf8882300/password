package com.tongu.search.config;

import com.tongu.search.exception.RbacErrorCodeEnum;
import com.tongu.search.exception.RbacException;
import com.tongu.search.service.UserService;
import com.tongu.search.util.JsonUtil;
import com.tongu.search.util.WebUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @desc 访问日志拦截器
 * @since 2018年1月17日
 */
@Aspect
@Component
public class VisitLogIntercept {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;
	
	/** 打印日志与响应时间 */
	@Around("within(com.tongu.search.controller..*Controller)")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		
		long curTime = System.currentTimeMillis();
		
		ServletRequestAttributes req = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		String method = pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName();
		String ip = WebUtil.getHost(req.getRequest());
		logger.info("IP：{}，访问：{}，参数：{}", ip, method, JsonUtil.toJson(pjp.getArgs()));

		// 权限检查
		String url = req.getRequest().getRequestURI();
		String token = req.getRequest().getHeader("X-Token");
		if(!userService.canAccess(url, ip, token)) {
			throw new RbacException(RbacErrorCodeEnum.AUTH_FAILURE);
		}

		Object result = pjp.proceed();
		logger.info("方法：{}，消耗时间：{}ms", method, System.currentTimeMillis() - curTime);
		
		return result;
		
	}
	
}
