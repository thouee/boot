package me.th.share.common.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 自定义异常处理类
 */
@Slf4j
// @RestControllerAdvice
public class GlobalExceptionHandlerV1 {
    private static final String ENV_PROD = "prod";
    private final MessageSource messageSource;
    private final Boolean isProd;

    public GlobalExceptionHandlerV1(@Value("${spring.profiles.active:dev}") String activeProfile,
                                    MessageSource messageSource) {
        this.messageSource = messageSource;
        this.isProd = new HashSet<>(Arrays.asList(activeProfile.split(","))).contains(ENV_PROD);
    }

    @ExceptionHandler({
            // 缺少 servlet 请求参数异常
            MissingServletRequestParameterException.class,
            // Servlet 请求绑定异常
            ServletRequestBindingException.class,
            // 类型不匹配异常
            TypeMismatchException.class,
            // 消息无法检索异常
            HttpMessageNotReadableException.class,
            // 缺少 servlet 请求部分异常
            MissingServletRequestPartException.class
    })
    public ErrorResponse badRequestException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(CommonResponseMode.BAD_REQUEST.getCode(), e.getMessage(), request);
    }


    @ExceptionHandler({
            // 没有发现处理程序异常
            NoHandlerFoundException.class
    })
    public ErrorResponse noHandlerFoundException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(CommonResponseMode.NOT_FOUND.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler({
            // 不支持的 Http 请求方法异常
            HttpRequestMethodNotSupportedException.class
    })
    public ErrorResponse httpRequestMethodNotSupportedException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(CommonResponseMode.METHOD_NOT_ALLOWED.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler({
            // 不接受的 Http 媒体类型异常
            HttpMediaTypeNotAcceptableException.class
    })
    public ErrorResponse httpMediaTypeNotAcceptableException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(CommonResponseMode.NOT_ACCEPTABLE.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler({
            // 不支持的 Http 媒体类型异常
            HttpMediaTypeNotSupportedException.class
    })
    public ErrorResponse httpMediaTypeNotSupportedException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(CommonResponseMode.UNSUPPORTED_MEDIA_TYPE.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler({
            // 异步请求超时异常
            AsyncRequestTimeoutException.class
    })
    public ErrorResponse asyncRequestTimeoutException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(CommonResponseMode.SERVICE_UNAVAILABLE.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler({
            // 请求路径参数缺失异常
            MissingPathVariableException.class,
            // Http 消息不可写异常
            HttpMessageNotWritableException.class,
            // 不支持转换异常
            ConversionNotSupportedException.class
    })
    public ErrorResponse handleServletException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(CommonResponseMode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler({
            // 参数绑定异常
            BindException.class
    })
    public ErrorResponse handleBindException(BindException e, WebRequest request) {
        log.error("参数绑定异常", e);
        return wrapperBindingResult(e.getBindingResult(), request);
    }

    @ExceptionHandler({
            // 方法参数无效异常
            MethodArgumentNotValidException.class
    })
    public ErrorResponse handleValidException(MethodArgumentNotValidException e, WebRequest request) {
        log.error("参数校验异常", e);
        return wrapperBindingResult(e.getBindingResult(), request);
    }

    /**
     * 包装绑定异常结果
     *
     * @param bindingResult 参数校验结果
     * @param request       -
     * @return ErrorResponse
     */
    private ErrorResponse wrapperBindingResult(BindingResult bindingResult, WebRequest request) {
        final List<String> errorMessage = new ArrayList<>();
        for (ObjectError aError : bindingResult.getAllErrors()) {
            final StringBuilder msg = new StringBuilder();
            if (aError instanceof FieldError) {
                msg.append(((FieldError) aError).getField().equals("; "));
            }
            msg.append(aError.getDefaultMessage() == null ? "" : aError.getDefaultMessage());
            errorMessage.add(msg.toString());
        }
        final String desc = isProd ? getLocaleMessage(CommonResponseMode.BAD_REQUEST.getCode(), "") :
                String.join("，", errorMessage);
        return new ErrorResponse(CommonResponseMode.BAD_REQUEST.getCode(), desc, request);
    }

    @ExceptionHandler({
            // 业务异常
            CodeBaseException.class
    })
    public ErrorResponse handleBizException(CodeBaseException e, WebRequest request) {
        log.error("业务异常：" + e.getMessage(), e);
        final ResponseMode aMode = e.getAMode();
        return new ErrorResponse(aMode.getCode(), aMode.getLocaleMessage(e.getArgs()), request);
    }

    @ExceptionHandler({
            // 余下为在此文件中定义的异常
            Exception.class
    })
    public ErrorResponse handleExceptionInternal(Exception e, WebRequest request) {
        log.error("未捕捉异常：" + e.getMessage(), e);
        Integer code = CommonResponseMode.INTERNAL_SERVER_ERROR.getCode();
        return new ErrorResponse(code, getLocaleMessage(code, e.getMessage()), request);
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
