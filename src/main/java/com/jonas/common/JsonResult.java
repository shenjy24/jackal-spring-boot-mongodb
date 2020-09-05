package com.jonas.common;

import com.jonas.util.GsonUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author shenjy
 * @date 2020/8/13
 * @description
 */
@Getter
@NoArgsConstructor
public class JsonResult<T> implements Serializable {
    private String code;
    private String message;
    private T data;

    public JsonResult(CodeStatus codeStatus) {
        this.code = codeStatus.getCode();
        this.message = codeStatus.getMessage();
    }

    public JsonResult(CodeStatus codeStatus, T data) {
        this.code = codeStatus.getCode();
        this.message = codeStatus.getMessage();
        this.data = data;
    }

    public JsonResult(String code, String message, T data) {
        this.code = code ;
        this.message =message ;
        this.data = data;
    }

    @Override
    public String toString() {
        return GsonUtils.toJson(this);
    }
}
