package me.th.share.common.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 扩展 Spring 提供的 ResponseEntityExceptionHandler 类
 */
@Slf4j
// @RestControllerAdvice
public class GlobalExceptionHandlerV2 extends ResponseEntityExceptionHandler {

    /**
     * 生产环境
     */
    private final static String ENV_PROD = "prod";
    private final MessageSource messageSource;
    private final Boolean isProd;

    public GlobalExceptionHandlerV2(@Value("${spring.profiles.active:dev}") String activeProfile,
                                    MessageSource messageSource) {
        this.messageSource = messageSource;
        this.isProd = new HashSet<>(Arrays.asList(activeProfile.split(","))).contains(ENV_PROD);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        log.info("请求异常：" + ex.getMessage(), ex);
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(new ErrorResponse(status, ex), headers, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
        log.info("参数绑定异常", ex);
        final ErrorResponse errorResponse = wrapperBindingResult(status, ex.getBindingResult());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.info("参数校验异常", ex);
        final ErrorResponse errorResponse = wrapperBindingResult(status, ex.getBindingResult());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.OK);
    }

    @ExceptionHandler(value = CodeBaseException.class)
    @ResponseBody
    public ErrorResponse handleBizException(CodeBaseException e) {
        log.error("业务异常：" + e.getMessage(), e);
        final ResponseMode aMode = e.getAMode();
        return new ErrorResponse(aMode.getCode(), aMode.getLocaleMessage(e.getArgs()));
    }

    /**
     * 包装绑定异常结果
     *
     * @param status        HTTP 状态码
     * @param bindingResult 参数校验结果
     * @return ErrorResponse
     */
    private ErrorResponse wrapperBindingResult(HttpStatus status, BindingResult bindingResult) {
        final List<String> errorMessage = new ArrayList<>();
        for (ObjectError aError : bindingResult.getAllErrors()) {
            final StringBuilder msg = new StringBuilder();
            if (aError instanceof FieldError) {
                msg.append(((FieldError) aError).getField().equals("; "));
            }
            msg.append(aError.getDefaultMessage() == null ? "" : aError.getDefaultMessage());
            errorMessage.add(msg.toString());
        }
        final String desc = isProd ? getLocaleMessage(status.value(), status.getReasonPhrase()) :
                String.join("，", errorMessage);
        return new ErrorResponse(status.value(), desc);
    }

    private String getLocaleMessage(Integer code, String defaultMsg) {
        try {
            return messageSource.getMessage("" + code, null, defaultMsg, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            log.warn("本地化异常消息发生异常：{}", code);
            return defaultMsg;
        }
    }
}
