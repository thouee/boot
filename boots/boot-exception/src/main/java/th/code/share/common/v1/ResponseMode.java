package th.code.share.common.v1;

public interface ResponseMode {

    Integer getCode();
    String getMessage();

    default String getLocaleMessage() {
        return getLocaleMessage(null);
    }

    String getLocaleMessage(Object[] args);
}
