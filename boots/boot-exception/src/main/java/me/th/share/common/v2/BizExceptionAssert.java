package me.th.share.common.v2;

import java.text.MessageFormat;

public interface BizExceptionAssert extends IResponseMode, BizAssert {

    @Override
    default BaseException newException(Object... args) {
        String message = MessageFormat.format(this.getMessage(), args);
        return new BizException(this, args, message);
    }

    @Override
    default BaseException newException(Throwable cause, Object... args) {
        String message = MessageFormat.format(this.getMessage(), args);
        return new BizException(this, args, message, cause);
    }
}
