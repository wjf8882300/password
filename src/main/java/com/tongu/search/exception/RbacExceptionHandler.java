package com.tongu.search.exception;

import com.tongu.search.model.vo.BaseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义异常处理
 * @author wangjf
 *
 */
@RestControllerAdvice
@Slf4j(topic = "error")
public class RbacExceptionHandler {

	@ExceptionHandler(value = RbacException.class)
	public BaseVO<String> processMatchException(RbacException ex) {
		log.warn("processMatchException {}", ex);
		return new BaseVO<String>(ex.getCode(), ex.getMessage(), null);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public BaseVO<String> processMatchException(MethodArgumentNotValidException ex) {
		log.warn("processMatchException {}", ex);
		BindingResult bindingResult = ex.getBindingResult();
		FieldError fieldError = bindingResult.getFieldError();
		return new BaseVO<String>(RbacErrorCodeEnum.INTERNAL_ERROR.getCode(), fieldError.getDefaultMessage(), null);
	}
	
	@ExceptionHandler(value = Exception.class)
	public BaseVO<String> processMatchException(Exception ex) {
		log.warn("processMatchException {}", ex);
		return new BaseVO<String>(RbacErrorCodeEnum.INTERNAL_ERROR.getCode(), RbacErrorCodeEnum.INTERNAL_ERROR.getMsg(), null);
	}
}
