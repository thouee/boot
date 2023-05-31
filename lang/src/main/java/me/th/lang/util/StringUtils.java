package me.th.lang.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public final class StringUtils {

    private StringUtils() {
    }

    /**
     * 提取模板中的变量名
     *
     * @param template      模板内容
     * @param paramRePrefix 正则表达式格式的变量前缀，如 <code>\\$\\{</code>
     * @param paramReSuffix 正则表达式格式的变量后缀，如 <code>\\}</code>
     * @return Set<String>
     */
    public static Set<String> extractParamNames(String template, String paramRePrefix, String paramReSuffix) {
        Pattern pattern = Pattern.compile(paramRePrefix + "\\w+" + paramReSuffix);
        Matcher matcher = pattern.matcher(template);
        Set<String> names = new HashSet<>();
        while (matcher.find()) {
            String name = matcher.group().replaceAll("(" + paramRePrefix + "|" + paramReSuffix + ")", "");
            names.add(name);
        }
        return names;
    }

    /**
     * 给模板中的变量赋值
     *
     * @param template      模板内容
     * @param paramRePrefix 正则表达式格式的变量前缀，如 <code>\\$\\{</code>
     * @param paramReSuffix 正则表达式格式的变量后缀，如 <code>\\}</code>
     * @param paramsMap     参数映射
     * @param defaultValue  在参数映射中找不到值时，默认设置的值
     * @return String
     */
    public static String assignValue(String template, String paramRePrefix, String paramReSuffix,
                                     Map<String, String> paramsMap, String defaultValue) {
        String dfv = (defaultValue == null) ? "" : defaultValue;
        Set<String> names = extractParamNames(template, paramRePrefix, paramReSuffix);
        AtomicReference<String> reference = new AtomicReference<>(template);
        names.forEach(name -> reference.set(reference.get()
                .replaceAll(paramRePrefix + name + paramReSuffix, paramsMap.getOrDefault(name, dfv))));
        return reference.get();
    }

    /**
     * 首字母小写
     *
     * @param str -
     * @return String
     */
    public static String lowerFirst(String str) {
        char[] cs = str.toCharArray();
        cs[0] += 32;
        return String.valueOf(cs);
    }
}
