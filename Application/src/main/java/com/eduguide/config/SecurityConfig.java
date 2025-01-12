package com.eduguide.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	
	
	
	// Oauth Configurations	
	 
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .csrf(csrf -> csrf.disable())
                 .authorizeHttpRequests(auth -> {
                     auth.requestMatchers("login").authenticated();
                     auth.anyRequest().permitAll();
                 })
                 .oauth2Login(
                		 oauth2 -> oauth2
                         .successHandler((request, response, authentication) -> {
                        	 
                        	 String username = null;
                        	 
                        	 if (authentication != null && authentication.getPrincipal() instanceof org.springframework.security.oauth2.core.user.OAuth2User) {
                                 org.springframework.security.oauth2.core.user.OAuth2User oauth2User = (org.springframework.security.oauth2.core.user.OAuth2User) authentication.getPrincipal();
                                 username = (String) oauth2User.getAttributes().get("name"); // Change "name" to the attribute used by your provider for the user's full name
                             }
                        	 
                        	 //System.out.println(username);
                             // Redirect to the frontend chatbot page after successful login       	 
                             response.sendRedirect("http://localhost:5173/chatbot");
                         })         	       
                  ); 
	        
	        return http.build();
	    }
	
	
	
}
