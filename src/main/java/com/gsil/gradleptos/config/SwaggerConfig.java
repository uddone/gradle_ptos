package com.gsil.gradleptos.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.components(new Components())
				.info(swaggerInfo());
	}
	
    private Info swaggerInfo() {
        return new Info()
				.title("평택 오송 gradle SWAGGER ")
                .description("GSiL STS REST api")
                .version("0.3");
    }

}


