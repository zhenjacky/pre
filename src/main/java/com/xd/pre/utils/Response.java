package com.xd.pre.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


/**
 * @Classname Response
 * @Description 响应信息主体
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-03-27 21:54
 * @Version 1.0
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;


    private int code = 200;
    private String msg;
    private Object data;

    public static Response ok() {
        Response response = new Response();
        response.setMsg("操作成功");
        return response;
    }

    public static Response ok(Object data) {
        Response response = new Response();
        response.setMsg("操作成功");
        response.setData(data);
        return response;
    }

    public static Response error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
    }

    public static Response error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static Response error(int code, String msg) {
        Response response = new Response();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }
}
