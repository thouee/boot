package me.th.limit.annotation;

import me.th.limit.constant.LimitType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Limiter {

    /**
     * 资源标识符前缀，最终 key 由前缀+(ip)+方法全限定名组成
     */
    String prefix() default "limiter";

    /**
     * 规定时间内访问次数
     */
    int count();

    /**
     * 规定时间，单位秒
     */
    int time();

    /**
     * 限流提示信息
     */
    String msg() default "访问太过频繁，请稍后再试";

    /**
     * 限流方式，默认接口限流或根据 ip 限流
     */
    LimitType type() default LimitType.DEFAULT;
}
