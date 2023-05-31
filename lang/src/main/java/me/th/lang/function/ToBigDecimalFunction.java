package me.th.lang.function;

import java.math.BigDecimal;

@FunctionalInterface
public interface ToBigDecimalFunction<T> {

    BigDecimal applyAsBigDecimal(T value);
}
