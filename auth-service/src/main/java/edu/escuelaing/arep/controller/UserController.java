package edu.escuelaing.arep.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    // 🔥 URL de login de Cognito
    private static final String COGNITO_LOGIN_URL = "https://us-east-1kusnhvoxd.auth.us-east-1.amazoncognito.com/login"
            + "?client_id=1lpipffql2lilim878esdoqcik"
            + "&response_type=code"
            + "&scope=email+openid+phone"
            + "&redirect_uri=https%3A%2F%2Fminitwitterarep.s3.us-east-1.amazonaws.com%2Findex.html";

    @GetMapping
    public ResponseEntity<Object> getUserInfo(@AuthenticationPrincipal OidcUser oidcUser, HttpServletRequest request) {
        if (oidcUser == null) {
            System.out.println("Usuario no autenticado, redirigiendo a Cognito...");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(COGNITO_LOGIN_URL));
            return ResponseEntity.status(302).headers(headers).build(); // 🔄 Redirección directa a Cognito
        }
        System.out.println("Usuario autenticado: " + oidcUser.getEmail());
        return ResponseEntity.ok(oidcUser.getClaims()); // 🔥 Retorna info del usuario autenticado
    }
}
