package me.th.share.common.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CodeBaseException extends RuntimeException {
    private static final long serialVersionUID = 8509654185681496275L;
    private final ResponseMode aMode;
    /**
     * 打印参数
     */
    private final Object[] args;
    /**
     * 异常信息
     */
    private final String message;
    /**
     * 堆栈信息
     */
    private final Throwable cause;

    public CodeBaseException(ResponseMode aMode) {
        this(aMode, null, aMode.getMessage(), null);
    }

    public CodeBaseException(ResponseMode aMode, String message) {
        this(aMode, null, message, null);
    }

    public CodeBaseException(ResponseMode aMode, Object[] args, String message) {
        this(aMode, args, message, null);
    }

    public CodeBaseException(ResponseMode aMode, Object[] args, String message, Throwable cause) {
        this.aMode = aMode;
        this.args = args;
        this.message = message;
        this.cause = cause;
    }
}
