package me.th.share.common.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponse extends BaseResponse {

    public ErrorResponse(Integer code, String message) {
        super(code, message);
    }

    public ErrorResponse(Integer code, String message, WebRequest request) {
        super(code, message, extractRequestURI(request));
    }

    public ErrorResponse(HttpStatus status, Exception e) {
        super(status.value(), status.getReasonPhrase() + ": " + e.getMessage());
    }

    public ErrorResponse(HttpStatus status, Exception e, WebRequest request) {
        super(status.value(), status.getReasonPhrase() + ": " + e.getMessage(), extractRequestURI(request));
    }

    private static String extractRequestURI(WebRequest request) {
        final String requestURI;
        if (request instanceof ServletWebRequest) {
            ServletWebRequest servletWebRequest = (ServletWebRequest) request;
            requestURI = servletWebRequest.getRequest().getRequestURI();
        } else {
            requestURI = request.getDescription(false);
        }
        return requestURI;
    }
}
