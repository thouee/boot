package me.th.share.common.v2;


public interface BizAssert {

    BaseException newException(Object... args);

    BaseException newException(Throwable cause, Object... args);

    default void isTrue(boolean expression, Object... args) {
        if (!expression) {
            throw newException(args);
        }
    }

    default void notNull(Object object, Object... args) {
        isTrue(object != null, args);
    }
}
