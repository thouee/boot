package me.th.limit.manager;

import me.th.limit.exception.LimiterException;
import org.apache.commons.lang3.StringUtils;

public interface LimiterManager {

    boolean tryAccess(Limiter limiter);

    default void checkParams(Limiter limiter) {
        String key = limiter.getKey();
        if (StringUtils.isBlank(key)) {
            throw new LimiterException("key 不可为空");
        }

        Integer limitNum = limiter.getLimitNum();
        if (limitNum <= 0) {
            throw new LimiterException("limitNum 必须设置正整数");
        }

        Integer seconds = limiter.getSeconds();
        if (seconds <= 0) {
            throw new LimiterException("seconds 必须设置正整数");
        }
    }
}
