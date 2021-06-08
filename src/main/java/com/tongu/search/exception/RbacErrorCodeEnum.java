package com.tongu.search.exception;

import lombok.Getter;

/**
 * 自定义异常编码
 * @author wangjf
 *
 */
public enum RbacErrorCodeEnum{
    /** */
	FILE_EMPTY(1000001, "file can't be empty"),
	FILE_OVER_SIZE(1000002, "file size can't more than 20M"),
	FILE_UPLOAD_FAILURE(1000003, "upload file error"),
	FILE_DOWNLOAD_FAILURE(1000004, "dowload file error"),
	CREATE_QR_CODE_FAILURE(1000005, "create qr code failure"),
	USER_EXISTS(1000005, "用户已经存在"),
	USER_NOT_EXISTS(1000006, "用户不存在"),
	ROLE_EXISTS(1000007, "角色已经存在"),
	ROLE_NOT_EXISTS(1000008, "角色不存在"),
	MENU_EXISTS(1000009, "菜单已经存在"),
	MENU_NOT_EXISTS(1000010, "菜单不存在"),
	SUPER_USER_CANNOT_DELETE(1000011, "超级管理员不允许删除"),
	NEWS_EXISTS(1000012, "新闻已经存在"),
	NEWS_NOT_EXISTS(1000013, "新闻不存在"),
	PRODUCT_EXISTS(1000014, "产品已经存在"),
	PRODUCT_NOT_EXISTS(1000015, "产品不存在"),
	AUTH_FAILURE(403, "未授权，请先登陆"),
	USER_LOGIN_FAILUER(400, "用户名或者密码错误"),
	DATA_EXISTS(1000016, "该数据已经存在，请勿重复添加"),
	VALID_ERROR(1000017, "项目类型、项目名称、密码均不能为空"),
	INTERNAL_ERROR(500, "内部错误，请联系客服"),
	DATA_NOT_EXISTS(1000033, "编辑的这条数据id不存在")
    ;
	
	@Getter
    private int code;
	@Getter
    private String msg;

    RbacErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static RbacErrorCodeEnum getEnum(int code) {
        for (RbacErrorCodeEnum error : RbacErrorCodeEnum.values()) {
            if (error.getCode() == code) {
                return error;
            }
        }
        return null;
    }
}

