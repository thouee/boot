package th.code.share.common.v3.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * CommonResponse
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class R<T> extends BaseResponse {

    private T data;

    protected R() {
        super(CommonResponseMode.SUCCESS.getCode(), CommonResponseMode.SUCCESS.getMessage());
    }

    protected R(T data) {
        super(CommonResponseMode.SUCCESS.getCode(), CommonResponseMode.SUCCESS.getMessage());
        this.data = data;
    }

    public static <T> R<T> ok(T data) {
        return new R<>(data);
    }

    public static R<Void> ok() {
        return new R<>();
    }
}
