package net.qqxh.sunflow.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈异常请求〉<br>
 * 〈功能详细描述〉
 *
 * @author jwy
 * @fileName: BadRequestException.java
 * @date: 2019/5/29 20:39
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Getter
public class BadRequestException extends RuntimeException {

    private Integer status = BAD_REQUEST.value();

    public BadRequestException(String msg) {
        super(msg);
    }

    public BadRequestException(HttpStatus status, String msg) {
        super(msg);
        this.status = status.value();
    }
}
