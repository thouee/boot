package me.th.share.common.v2;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonResponseMode implements IResponseMode {
    SUCCESS(5000, "成功."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "Bad Request."),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Not Found."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "Method Not Allowed."),
    NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE.value(), "Not Acceptable."),
    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT.value(), "Request Timeout."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "Unsupported Media Type."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error."),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), "Service Unavailable."),
    ;

    private Integer code;
    private String message;

    CommonResponseMode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
