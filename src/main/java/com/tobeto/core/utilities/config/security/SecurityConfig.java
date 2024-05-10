package com.tobeto.core.utilities.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tobeto.core.utilities.filter.jwt.JwtAuthorizationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthorizationFilter jwtAuthorizationFilter;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/api/v1/login").permitAll().requestMatchers("/api/v1/user/create").hasRole("Admin")
				.requestMatchers("/api/v1/user/update").hasRole("Admin").requestMatchers("/api/v1/user/delete")
				.hasRole("Admin").requestMatchers("/api/v1/shelf/create").hasRole("Admin")
				.requestMatchers("/api/v1/shelf/update").hasRole("Admin").requestMatchers("/api/v1/shelf/delete")
				.hasRole("Admin").requestMatchers("/api/v1/category/create").hasRole("Admin")
				.requestMatchers("/api/v1/category/update").hasRole("Admin").requestMatchers("/api/v1/category/delete")
				.hasRole("Admin").requestMatchers("/api/v1/product/create").hasRole("Admin")
				.requestMatchers("/api/v1/product/update").hasRole("Admin").requestMatchers("/api/v1/product/delete")
				.hasRole("Admin").requestMatchers("/api/v1/product/accept").hasAnyRole("Admin", "Warehouse Supervisor")
				.requestMatchers("/api/v1/product/sale").hasAnyRole("Admin", "Warehouse Supervisor")
				.requestMatchers("/api/v1/customer/create").hasRole("Admin").requestMatchers("/api/v1/customer/update")
				.hasRole("Admin").requestMatchers("/api/v1/customer/delete").hasRole("Admin")
				.requestMatchers("/api/v1/supplier/create").hasRole("Admin").requestMatchers("/api/v1/supplier/update")
				.hasRole("Admin").requestMatchers("/api/v1/customer/delete").hasRole("Admin")
				.requestMatchers("/api/v1/invoice/create").hasAnyRole("Admin", "Warehouse Supervisor")
				.requestMatchers("/api/v1/invoice/update").hasRole("Admin")
				.requestMatchers("/api/v1/invoice/cancellation").hasRole("Admin").anyRequest().authenticated())
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
