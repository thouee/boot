package th.code.share.common.v3.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ErrorResponse
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ER extends BaseResponse {

    public ER(Integer code, String message) {
        super(code, message);
    }
}
