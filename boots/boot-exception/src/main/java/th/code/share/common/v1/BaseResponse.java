package th.code.share.common.v1;

import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseResponse {

    private Integer code;
    private String message;
    private Date timestamp = new Date();
    private String path;

    public BaseResponse() {
    }

    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(Integer code, String message, String path) {
        this.code = code;
        this.message = message;
        this.path = path;
    }
}
