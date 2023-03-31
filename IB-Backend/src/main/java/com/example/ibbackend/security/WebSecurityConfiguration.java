package com.example.IBBackend.security;

//import com.example.IBBackend.security.jwt.JwtRequestFilter;
import com.example.IBBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration {
//	@Autowired
//	private JwtRequestFilter jwtRequestFilter;
	@Autowired
	public UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return authProvider;
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//		http.cors().and();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.csrf().disable().authorizeHttpRequests((requests) -> requests
						.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/api/user/login")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/api/user/signup")).permitAll()
						.anyRequest().authenticated());
//		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); //JWT procesiramo pre autentikacije
		http.headers().frameOptions().sameOrigin();
		http.authenticationProvider(authenticationProvider());
		return http.build();
	}

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
}