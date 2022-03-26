package io.github.ludongrong.dbcoder.exception;

/**
 * 异常.
 *
 * @author <a href="mailto:736779458@qq.com">ludongrong</a>
 * @since 2020-12-02
 */
public class BadGatewayException extends RuntimeException {

    private static final long serialVersionUID = 5626644047113997653L;

    public BadGatewayException(String message) {
        super(message);
    }

    public BadGatewayException(String message, Throwable cause) {
        super(message, cause);
    }
}