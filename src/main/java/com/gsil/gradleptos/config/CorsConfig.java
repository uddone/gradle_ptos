package com.gsil.gradleptos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer{
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
		// CORS 적용할 URL 패턴
		.addMapping("/**")
		// 자원 공유할 오리진 지정
		.allowedOriginPatterns("*")
		// 요청 허용 메서드
		.allowedMethods("GET", "POST", "PUT", "DELETE")
		// 요청 허용 헤더
		.allowedHeaders("*")
		// 쿠키 비허용 csrf 끄기
		.allowCredentials(false)
		.maxAge(3000);
	}

}
