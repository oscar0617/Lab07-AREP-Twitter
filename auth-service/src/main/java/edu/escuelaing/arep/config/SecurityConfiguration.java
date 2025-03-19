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
            .csrf(csrf -> csrf.disable()) // 🔹 Desactivar CSRF para evitar bloqueos
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/users").authenticated() // 🔐 Exigir autenticación en `/users`
                .anyRequest().permitAll() // 🔹 Permitir acceso sin autenticación a todo lo demás
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("https://us-east-1kusnhvoxd.auth.us-east-1.amazoncognito.com/login"
                        + "?client_id=1lpipffql2lilim878esdoqcik"
                        + "&response_type=code"
                        + "&scope=email+openid+phone"
                        + "&redirect_uri=https%3A%2F%2Fminitwitterarep.s3.us-east-1.amazonaws.com%2Findex.html") // ✅ Redirigir a Cognito
                .defaultSuccessUrl("https://minitwitterarep.s3.us-east-1.amazonaws.com/index.html", true) // ✅ Redirigir a S3 tras login
            )

            .build();
    }
}
