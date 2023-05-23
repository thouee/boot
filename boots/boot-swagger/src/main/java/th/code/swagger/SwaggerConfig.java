package th.code.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                // .apiBasePackage("th.code.controller") // 设置 swagger 扫描的 controller 路径
                .apiBaseClassAnnotation(RestController.class) // 设置 swagger 扫描的指定注解
                .title("Boot-Swagger项目")
                .description("后台接口文档")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
