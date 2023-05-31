package me.th.limit.exception;

public class LimiterException extends RuntimeException {

    private static final long serialVersionUID = -763335476691948754L;

    public LimiterException() {
        super();
    }

    public LimiterException(String message) {
        super(message);
    }
}
