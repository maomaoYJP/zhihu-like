package com.maomao.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author maomao
 * 2022/7/20 21:28
 */

//自定义异常，用于页面找不到情况
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PageNotFindException extends RuntimeException{
    public PageNotFindException() {
        super();
    }

    public PageNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageNotFindException(String message) {
        super(message);
    }
}
