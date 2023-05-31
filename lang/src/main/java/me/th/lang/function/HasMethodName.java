package me.th.lang.function;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

public interface HasMethodName extends Serializable {

    private SerializedLambda getSerializedLambda() {
        try {
            Method method = getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            return (SerializedLambda) method.invoke(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得方法名
     *
     * @return String
     */
    default String getMethodName() {
        SerializedLambda serializedLambda = getSerializedLambda();
        String implMethodName = serializedLambda.getImplMethodName();
        String implClass = serializedLambda.getImplClass();
        String[] split = implClass.split("/");
        return split[split.length - 1] + "#" + implMethodName;
    }
}
