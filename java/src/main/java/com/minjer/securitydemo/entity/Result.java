package com.minjer.securitydemo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Minjer
 * @since 2025-07-21
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success() {
        return new Result<>(200, "Success", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "Success", data);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(500, message, null);
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
