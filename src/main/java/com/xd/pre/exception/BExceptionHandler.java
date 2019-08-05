package com.xd.pre.exception;

import com.xd.pre.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;

/**
 * @Classname BExceptionHandler
 * @Description 自定义异常处理
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-03-29 13:23
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class BExceptionHandler {


    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BaseException.class)
    public Response handleRRException(BaseException e) {
        return Response.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Response handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return Response.error(404, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Response handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return Response.error(300, "数据库中已存在该记录");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Response handleAuthorizationException(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        return Response.error(403, "没有权限，请联系管理员授权");
    }

    @ExceptionHandler(AccountExpiredException.class)
    public Response handleAccountExpiredException(AccountExpiredException e) {
        log.error(e.getMessage(), e);
        return Response.error(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public Response handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error(e.getMessage(), e);
        return Response.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Response.error();
    }

    @ExceptionHandler(SQLException.class)
    public Response handleSQLException(SQLException e) {
        log.error(e.getMessage(), e);
        return Response.error();
    }

}
