package me.th.lang.util;

import java.lang.reflect.Field;

/**
 * Bean 工具类
 */
public final class BeanUtils {

    private BeanUtils() {
    }

    /**
     * 合并 sourceBean 到 targetBean，要求两个 bean 中的属性名一致
     *
     * @param sourceBean -
     * @param targetBean -
     * @return Object
     */
    public static Object mergeData(Object sourceBean, Object targetBean) throws IllegalAccessException {
        Field[] sourceFields = sourceBean.getClass().getDeclaredFields();
        Field[] targetFields = targetBean.getClass().getDeclaredFields();
        for (Field sourceField : sourceFields) {
            for (Field targetField : targetFields) {
                if (sourceField.getName().equals(targetField.getName())) {
                    sourceField.setAccessible(true);
                    targetField.setAccessible(true);
                    if (sourceField.get(sourceBean) != null) {
                        targetField.set(targetBean, sourceField.get(sourceBean));
                    }
                }
            }
        }
        return targetBean;
    }
}
