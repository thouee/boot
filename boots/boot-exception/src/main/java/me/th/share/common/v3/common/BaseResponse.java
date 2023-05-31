package me.th.share.common.v3.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class BaseResponse {

    private Integer code;
    private String message;

    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
