package th.code.share.common.v3.exception;

import lombok.Getter;

/**
 * ExceptionResponseMode<br>
 */
@Getter
public enum ERMode implements BizExceptionAssert {
    SERVER_ERROR(6001, "服务器内部异常"),
    TOKEN_IS_NULL(7001, "Token 不能为空"),
    TOKEN_IS_INVALID(7002, "Token 无效"),
    TOKEN_IS_EXPIRED(7003, "Token 已过期"),
    USER_NOT_EXIST(8001, "用户不存在"),
    USER_USERNAME_EXIST(8002, "用户名已存在"),
    USER_PASSWORD_ERROR(8003, "用户名或密码错误"),
    USER_IS_DISABLED(8004, "用户被禁用"),
    ;

    private Integer code;
    private String message;

    ERMode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
