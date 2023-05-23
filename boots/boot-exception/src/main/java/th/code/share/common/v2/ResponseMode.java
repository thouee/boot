package th.code.share.common.v2;

import lombok.Getter;

@Getter
public enum ResponseMode implements BizExceptionAssert {
    SERVER_ERROR(6001, "服务器内部异常."),
    USER_NOT_FOUNT(7002, "用户不存在.")
    ;

    private Integer code;
    private String message;

    ResponseMode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
