package dev.service.cloud.loan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 엔드포인트에 대해 CORS 허용
                .allowedOrigins("http://localhost:3000", "http://{본인 IP 주소}:3000") // 허용할 출처 (Origin)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 허용할 헤더
                .allowCredentials(true) // 인증 정보 허용 (쿠키 등)
                .maxAge(3600); // 브라우저에서 요청을 캐시하는 시간 (초 단위)
    }
}
