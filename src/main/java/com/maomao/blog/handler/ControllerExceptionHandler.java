package com.maomao.blog.handler;

import com.maomao.blog.exception.PageNotFindException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * @author maomao
 * 2022/7/20 21:19
 */

@ControllerAdvice
public class ControllerExceptionHandler {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({PageNotFindException.class})
    //在全局异常拦截前面，用于捕获404异常
    public ModelAndView pageNotFindExceptionHandler(HttpServletRequest request, Exception e){
        logger.error("Request URL: {}, Exception : {}", request.getRequestURI(),e);
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURI());
        mv.addObject("Exception", e);
        mv.setViewName("/error/404");
        return mv;
    }

    @ExceptionHandler({Exception.class})
    //全局异常拦截，自定义异常页面
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e){
        logger.error("Request URL: {}, Exception : {}", request.getRequestURI(),e);
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURI());
        mv.addObject("Exception", e);
        mv.setViewName("/error/error");
        return mv;
    }

}
