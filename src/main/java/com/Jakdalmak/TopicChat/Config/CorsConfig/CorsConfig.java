package com.Jakdalmak.TopicChat.Config.CorsConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** Controller 에 대한 CORS 문제 해결 위한 configuration */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Windows 예시: "file:///C:/MyAppImages/"
        // Linux/Mac 예시: "file:/home/user/MyAppImages/"
        registry.addResourceHandler("/images/**").addResourceLocations("file:///C:\\image\\");
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .exposedHeaders("Set-Cookie")
                .allowedOrigins("http://localhost:5173", "http://lunaveldev.duckdns.org/")
                .allowCredentials(true) // 크로스-도메인 쿠키를 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 브라우저에서 다음과 같은 요청을 전송하는 것을 허용
                .allowedHeaders("*");
    }
}

