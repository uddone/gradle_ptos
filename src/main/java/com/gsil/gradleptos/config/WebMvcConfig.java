package com.gsil.gradleptos.config;

import com.gsil.gradleptos.Constants;
import com.gsil.gradleptos.util.LoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/upload/**")
        .addResourceLocations(Constants.LINUX_FILE_URL);
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		// request, response log를 위한 HandlerInterceptor 구현
		registry.addInterceptor(new LoggerInterceptor())
		.addPathPatterns("/**")
		.excludePathPatterns("/DashBoard/**");
	}
    
    

}
