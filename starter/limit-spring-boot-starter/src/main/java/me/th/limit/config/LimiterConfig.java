package me.th.limit.config;

import me.th.limit.manager.GuavaLimiter;
import me.th.limit.manager.LimiterManager;
import me.th.limit.manager.RedisLimiter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

@Configuration
public class LimiterConfig {

    @Bean
    @ConditionalOnProperty(name = "limiter.type", havingValue = "local")
    public LimiterManager guavaLimiter() {
        return new GuavaLimiter();
    }

    @Bean
    @ConditionalOnProperty(name = "limiter.type", havingValue = "redis")
    public LimiterManager redisLimiter(StringRedisTemplate stringRedisTemplate,
                                       RedisScript<Boolean> rateLimiterScript) {
        return new RedisLimiter(stringRedisTemplate, rateLimiterScript);
    }

    @Bean
    @ConditionalOnProperty(name = "limiter.type", havingValue = "redis")
    public DefaultRedisScript<Boolean> loadRedisScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("lua/rateLimiter.lua"));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }
}
