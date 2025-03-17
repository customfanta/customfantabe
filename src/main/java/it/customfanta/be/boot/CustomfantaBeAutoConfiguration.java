package it.customfanta.be.boot;

import it.customfanta.be.interceptor.UserDataInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@AutoConfiguration
public class CustomfantaBeAutoConfiguration implements WebMvcConfigurer {

    private static final List<String> EXCLUDED_PATH_PATTERNS =
            Arrays.asList("/v3/api-docs", "/v3/api-docs/*", "/swagger-ui.html", "/swagger-ui/", "/swagger-ui/*",
                    "/actuator/health/readiness", "/actuator/health/liveness", "/actuator/health");

    @Autowired
    private UserDataInterceptor userDataInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userDataInterceptor).excludePathPatterns(EXCLUDED_PATH_PATTERNS);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://customfanta.github.io", "http://localhost:8080")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
