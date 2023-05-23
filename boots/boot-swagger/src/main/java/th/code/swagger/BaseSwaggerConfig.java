package th.code.swagger;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

public abstract class BaseSwaggerConfig {

    public abstract SwaggerProperties swaggerProperties();

    @Bean
    public Docket createRestApi() {
        SwaggerProperties swaggerProperties = swaggerProperties();
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(apiInfo(swaggerProperties()));
        if (swaggerProperties.getApiBasePackage() != null) {
            docket.select().apis(RequestHandlerSelectors.basePackage(swaggerProperties.getApiBasePackage()))
                    .paths(PathSelectors.any());
        } else {
            docket.select().apis(RequestHandlerSelectors.withClassAnnotation(swaggerProperties.getApiBaseClassAnnotation()))
                    .paths(PathSelectors.any());
        }

        if (swaggerProperties.isEnableSecurity()) {
            docket.securitySchemes(Collections.singletonList(securitySchema()))
                    .securityContexts(Collections.singletonList(securityContext()));
        }
        return docket;
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle() + " 服务API接口文档")
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(),
                        swaggerProperties.getContactEmail()))
                .build();
    }

    private ApiKey securitySchema() {
        // 设置请求头信息
        return new ApiKey("token", "accessToken", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .operationSelector(operationContext -> true)
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("xxx", "描述信息");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
    }
}
