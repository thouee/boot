package me.th.serializer.dict;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import org.springframework.stereotype.Component;

/**
 * @author hout
 * @apiNote 字典注解序列化拦截器
 * @date 2023/6/2
 */
@Component(value = "dictAnnotationIntrospector")
public class DictAnnotationIntrospector extends NopAnnotationIntrospector {

    private static final long serialVersionUID = -4603841207503214222L;

    @Override
    public Object findSerializer(Annotated am) {
        Dict dict = am.getAnnotation(Dict.class);
        if (dict != null) {
            return DictSerializer.class;
        }
        return null;
    }
}
