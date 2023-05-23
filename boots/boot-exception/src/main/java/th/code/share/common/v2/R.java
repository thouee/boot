package th.code.share.common.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * CommonResponse
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class R<T> extends BaseResponse {

    private T data;

    public R(T data) {
        super(CommonResponseMode.SUCCESS.getCode(), CommonResponseMode.SUCCESS.getMessage());
        this.data = data;
    }
}
