package com.example.ibbackend.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.addAllowedOrigin("*"); // Add your allowed origins here
        configuration.addAllowedMethod("*"); // Add your allowed HTTP methods here
        configuration.addAllowedHeader("*"); // Add your allowed headers here
        configuration.setAllowCredentials(true); // Allow cookies to be sent along with the request
        configuration.setMaxAge(3600L); // Set the maximum age of the CORS preflight request

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply the CORS configuration to all paths

        return source;
    }

    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }
}
