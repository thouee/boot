package me.th.limit.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.List;

@Slf4j
public class RedisLimiter implements LimiterManager {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisScript<Boolean> rateLimiterScript;

    public RedisLimiter(StringRedisTemplate stringRedisTemplate,
                        RedisScript<Boolean> rateLimiterScript) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.rateLimiterScript = rateLimiterScript;
    }

    @Override
    public boolean tryAccess(Limiter limiter) {
        Boolean access = stringRedisTemplate.execute(rateLimiterScript, List.of(limiter.getKey()), limiter.getLimitNum().toString(),
                limiter.getSeconds().toString());
        log.debug("{} access：{}", limiter.getKey(), access);

        if (access == null) {
            return false;
        }
        return access;
    }
}
