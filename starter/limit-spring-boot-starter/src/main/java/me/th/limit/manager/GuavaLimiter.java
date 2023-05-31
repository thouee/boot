package me.th.limit.manager;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@SuppressWarnings("all")
public class GuavaLimiter implements LimiterManager {

    private final Map<String, RateLimiter> limiterMap = Maps.newConcurrentMap();

    @Override
    public boolean tryAccess(Limiter limiter) {
        RateLimiter rateLimiter = getRateLimiter(limiter);
        if (rateLimiter == null) {
            return false;
        }

        boolean acquire = rateLimiter.tryAcquire();
        log.debug("{} access：{}", limiter.getKey(), acquire);

        return acquire;
    }

    private RateLimiter getRateLimiter(Limiter limiter) {
        return limiterMap.computeIfAbsent(limiter.getKey(),
                value -> {
                    double qps = limiter.getLimitNum().doubleValue() / limiter.getSeconds().doubleValue();
                    return RateLimiter.create(qps);
                });
    }
}
