package me.th.lang.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 枚举工具类
 */
public final class EnumUtils {

    private EnumUtils() {
    }

    /**
     * 是否存在 value
     *
     * @param clazz  枚举类
     * @param getFun 获得比较值的 function
     * @param value  比较值
     * @return boolean
     */
    public static <E extends Enum<E>, T> boolean isExist(Class<E> clazz, Function<E, T> getFun, T value) {
        if (value == null) {
            return false;
        }
        E[] enumConstants = clazz.getEnumConstants();
        return Arrays.stream(enumConstants).map(getFun).anyMatch(e -> e.equals(value));
    }

    /**
     * 根据 value 获取枚举
     *
     * @param clazz  枚举类
     * @param getFun 获得比较值的 function
     * @param value  比较值
     * @return List<E>
     */
    public static <E extends Enum<E>, T> List<E> valueOf(Class<E> clazz, Function<E, T> getFun, T value) {
        E[] enumConstants = clazz.getEnumConstants();
        return Arrays.stream(enumConstants).filter(e -> {
            if (value == null) {
                return getFun.apply(e) == null;
            } else {
                return value.equals(getFun.apply(e));
            }
        }).collect(Collectors.toList());
    }
}
