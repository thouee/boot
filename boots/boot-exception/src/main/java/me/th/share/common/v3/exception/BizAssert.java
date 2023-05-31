package me.th.share.common.v3.exception;


/**
 * 业务断言类，断言为真抛出异常
 */
public interface BizAssert {

    BaseException newException(Object... args);

    BaseException newException(Throwable cause, Object... args);

    default void isTrue(boolean expression, Object... args) {
        if (expression) {
            throw newException(args);
        }
    }

    default void isFalse(boolean expression, Object... args) {
        isTrue(!expression, args);
    }

    default void notNull(Object object, Object... args) {
        isTrue(object != null, args);
    }

    default void isNull(Object object, Object... args) {
        isTrue(object == null, args);
    }
}
