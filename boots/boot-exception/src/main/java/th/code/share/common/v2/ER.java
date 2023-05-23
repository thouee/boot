package th.code.share.common.v2;

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
