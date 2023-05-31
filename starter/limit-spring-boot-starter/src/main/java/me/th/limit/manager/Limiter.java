package me.th.limit.manager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Limiter {

    /**
     * 资源标识符，唯一
     */
    private String key;

    /**
     * 规定时间内访问次数
     */
    private Integer limitNum;

    /**
     * 规定时间，单位秒
     */
    private Integer seconds;
}
