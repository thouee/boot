package me.th.swagger;

import lombok.Builder;
import lombok.Data;

import java.lang.annotation.Annotation;

@Data
@Builder
public class SwaggerProperties {

    /**
     * API文档生成基础路径，与注解二选一即可，优先级为路径 > 注解
     */
    private String apiBasePackage;
    /**
     * API文档生成扫描的指定注解，与路径二选一即可，优先级为路径 > 注解
     */
    private Class<? extends Annotation> apiBaseClassAnnotation;
    /**
     * 是否要启用登录认证
     */
    private boolean enableSecurity;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;
    /**
     * 文档版本
     */
    private String version;
    /**
     * 文档联系人姓名
     */
    private String contactName;
    /**
     * 文档联系人网址
     */
    private String contactUrl;
    /**
     * 文档联系人邮箱
     */
    private String contactEmail;
}
