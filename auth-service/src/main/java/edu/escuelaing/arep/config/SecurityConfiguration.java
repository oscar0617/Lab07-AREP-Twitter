package edu.escuelaing.arep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/users").authenticated()
                .anyRequest().permitAll()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("https://us-east-11xrvet0n4.auth.us-east-1.amazoncognito.com/login"
                        + "?client_id=37hclu5v65ut7f6q978aaj10ss"
                        + "&response_type=code"
                        + "&scope=email+openid+phone"
                        + "&redirect_uri=https%3A%2F%2Fminitwitters3.s3.us-east-1.amazonaws.com%2Findex.html")
                .defaultSuccessUrl("https://minitwitters3.s3.us-east-1.amazonaws.com/index.html", true) 
            )

            .build();
    }
}