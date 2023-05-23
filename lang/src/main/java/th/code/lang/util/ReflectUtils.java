package th.code.lang.util;

import lombok.Getter;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射工具类
 */
public final class ReflectUtils {

    private ReflectUtils() {
    }

    /**
     * 对象添加额外属性
     *
     * @param source        源对象
     * @param addProperties 额外属性集合
     * @return Object
     */
    public static Object objAddProperties(Object source, Map<String, Object> addProperties) {
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] propertyDescriptors = propertyUtilsBean.getPropertyDescriptors(source);
        Map<String, Class<?>> propertyMap = new HashMap<>();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (!"class".equalsIgnoreCase(propertyDescriptor.getName())) {
                // 原对象中的属性
                propertyMap.put(propertyDescriptor.getName(), propertyDescriptor.getPropertyType());
            }
        }
        // 额外添加的属性
        addProperties.forEach((k, v) -> propertyMap.put(k, v.getClass()));
        DynamicBean dynamicBean = new DynamicBean(source.getClass(), propertyMap);
        propertyMap.forEach((k, v) -> {
            if (!addProperties.containsKey(k)) {
                try {
                    dynamicBean.setValue(k, propertyUtilsBean.getNestedProperty(source, k));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        addProperties.forEach(dynamicBean::setValue);
        return dynamicBean.getTarget();
    }

    static class DynamicBean {
        @Getter
        private final Object target;
        private final BeanMap beanMap;

        public DynamicBean(Class<?> clazz, Map<String, Class<?>> propertyMap) {
            this.target = generateBean(clazz, propertyMap);
            this.beanMap = BeanMap.create(this.target);
        }

        public void setValue(String property, Object value) {
            beanMap.put(property, value);
        }

        public Object getValue(String property) {
            return beanMap.get(property);
        }

        private Object generateBean(Class<?> clazz, Map<String, Class<?>> propertyMap) {
            BeanGenerator beanGenerator = new BeanGenerator();
            if (clazz != null) {
                beanGenerator.setSuperclass(clazz);
            }
            BeanGenerator.addProperties(beanGenerator, propertyMap);
            return beanGenerator.create();
        }
    }
}
