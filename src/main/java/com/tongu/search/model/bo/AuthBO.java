package com.tongu.search.model.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ：wangjf
 * @date ：2020/11/18 11:43
 * @description：provider-tennis
 * @version: v1.1.0
 */
@Data
public class AuthBO implements Serializable {
    @NotNull(message = "id不能为空")
    private String id;
    @NotNull(message = "code不能为空")
    private Integer code;
}
