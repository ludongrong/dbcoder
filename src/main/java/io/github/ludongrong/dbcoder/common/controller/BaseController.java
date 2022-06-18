package io.github.ludongrong.dbcoder.common.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.github.ludongrong.dbcoder.common.controller.dto.BaseDto;
import io.github.ludongrong.dbcoder.common.exception.BadGatewayException;

@Slf4j
@ControllerAdvice
public class BaseController {

    static final String _BAD_REQUEST_MSG = "An error occurred with the application parameters.";

    static final String _BAD_GATEWAY_MSG = "An error occurred with the application went wrong";

    /**
     * 参数校验异常
     * 
     * <pre>
     * 该状态码表示请求报文中存在语法错误。当错误发生时，需修改请求的内容后再次发送请求。
     * 另外，浏览器会像 200 OK 一样对待该状态码。
     * </pre>
     * 
     * @param methodArgumentNotValidException {@code MethodArgumentNotValidException}}
     * @return {@code ResponseDto}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {

        log.info("Exception:{}", methodArgumentNotValidException.getMessage());
        log.error("Exception", methodArgumentNotValidException);

        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String msg = fieldError.getDefaultMessage();
            if (null != msg) {
                return new BaseDto(msg);
            }
        }

        return new BaseDto(_BAD_REQUEST_MSG);
    }

    @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseDto illegalArgumentException() {
        return new BaseDto(_BAD_REQUEST_MSG);
    }

    /**
     * 应用程序出错
     * 
     * <pre>
     * 该状态码表明扮演网关或代理角色的服务器，从上游服务器中接收到的响应是无效的
    
     * 注意，502 错误通常不是客户端能够修复的，而是需要由途经的 Web 服务器或者代理服务器对其进行修复
    
     * 以下情况会出现502：
    
     * 502.1 - CGI （通用网关接口）应用程序超时。
     * 502.2 - CGI （通用网关接口）应用程序出错。
     * </pre>
     * 
     * @param exception {@code Exception}
     * @return {@code ResponseDto}
     */
    @ExceptionHandler({ BadGatewayException.class, Exception.class })
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ResponseBody
    public BaseDto handleDefaultException(Exception exception) {
        log.info("Exception:{}", exception.getMessage());
        log.error("Exception", exception);
        return new BaseDto(_BAD_GATEWAY_MSG);
    }

}
