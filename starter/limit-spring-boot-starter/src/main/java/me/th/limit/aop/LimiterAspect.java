package me.th.limit.aop;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.th.limit.annotation.Limiter;
import me.th.limit.condition.LimiterAspectCondition;
import me.th.limit.constant.LimitType;
import me.th.limit.exception.LimiterException;
import me.th.limit.manager.LimiterManager;
import me.th.limit.util.IpUtils;
import me.th.limit.util.RequestHolder;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Order(-1)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Conditional(LimiterAspectCondition.class)
public class LimiterAspect {

    @Setter(onMethod_ = @Autowired)
    private LimiterManager limiterManager;

    @Pointcut("@annotation(me.th.limit.annotation.Limiter)")
    private void doLimit() {
    }

    @Before("doLimit()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Limiter limiter = method.getAnnotation(Limiter.class);
        if (limiter != null) {
            String methodName = method.getName();
            String className = signature.getDeclaringTypeName();

            boolean useIp = false;
            String ip = "";
            if (LimitType.IP.equals(limiter.type())) {
                useIp = true;
                ip = IpUtils.getIP(request);
                if (StringUtils.isBlank(ip)) {
                    useIp = false;
                    log.warn("ip 获取失败，转为接口限流模式");
                }
            }

            String key = new StringBuffer().append(limiter.prefix()).append(useIp ? ("#" + ip + "#") : "#")
                    .append(className).append("#").append(methodName).toString();

            me.th.limit.manager.Limiter limit = new me.th.limit.manager.Limiter();
            limit.setKey(key);
            limit.setLimitNum(limiter.count());
            limit.setSeconds(limiter.time());
            limiterManager.checkParams(limit);

            log.debug("limit key:[{}], count:[{}], time:[{}]", key, limit.getLimitNum(), limit.getSeconds());

            boolean access = limiterManager.tryAccess(limit);
            if (!access) {
                throw new LimiterException(limiter.msg());
            }
        }
    }
}
