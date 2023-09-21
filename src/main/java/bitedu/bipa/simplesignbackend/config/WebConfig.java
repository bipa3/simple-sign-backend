package bitedu.bipa.simplesignbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 엔드포인트에 대한 CORS 설정을 적용
                .allowedOrigins("http://localhost:3000") // 허용할 오리진 (여기서는 로컬 개발 서버)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
                .allowCredentials(true) // 자격 증명 허용 여부
                .maxAge(3600); // 사전 검사 (Preflight) 요청 캐시 시간 (초)
    }
}