package me.th.limit.condition;

import me.th.limit.constant.LimiterConstant;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LimiterAspectCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().containsProperty(LimiterConstant.LIMITER_TYPE);
    }
}
