package com.tobeto.core.utilities.config.webConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("*");
				registry.addMapping("/**").allowedOrigins("http://localhost:4200") // İzin verilen origin (Angular
																					// uygulamasının URL'si)
						.allowedMethods("GET", "POST", "PUT", "DELETE") // İzin verilen HTTP metodları
						.allowedHeaders("*").allowCredentials(true);
			}
		};
	}
}
