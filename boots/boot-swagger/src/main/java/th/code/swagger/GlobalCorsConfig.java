package th.code.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class GlobalCorsConfig {

    /**
     * 跨域处理
     *
     * @return CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        // 设置访问源地址
        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        // 设置访问源请求头
        corsConfiguration.setAllowedHeaders(List.of("*"));
        // 设置访问源请求方法
        corsConfiguration.setAllowedMethods(List.of("*"));
        // 有效期 1800 s
        corsConfiguration.setMaxAge(1800L);

        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        // 返回新的 CorsFilter
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
