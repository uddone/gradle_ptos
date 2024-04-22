package com.gsil.gradleptos;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GradlePtosApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		// 환경 변수를 시스템 환경 변수로 설정
		System.setProperty("spring.datasource.url", dotenv.get("DATABASE_URL"));
		System.setProperty("spring.datasource.username", dotenv.get("DATABASE_USER"));
		System.setProperty("spring.datasource.password", dotenv.get("DATABASE_PASSWORD"));

		SpringApplication.run(GradlePtosApplication.class, args);
	}
}
/*
*
* Dotenv dotenv = Dotenv.load();
		String dbUrl = dotenv.get("DATABASE_URL");
		String dbUser = dotenv.get("DATABASE_USER");
		String dbPassword = dotenv.get("DATABASE_PASSWORD");
*
* */