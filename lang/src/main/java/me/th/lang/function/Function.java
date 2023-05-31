package me.th.lang.function;

@FunctionalInterface
public interface Function<T> extends HasMethodName {

    /**
     * 执行方法并返回
     *
     * @return T
     */
    T apply();
}
