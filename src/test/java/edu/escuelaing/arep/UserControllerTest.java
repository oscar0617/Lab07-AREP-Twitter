package edu.escuelaing.arep;

import edu.escuelaing.arep.controller.UserController;
import edu.escuelaing.arep.model.User;
import edu.escuelaing.arep.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testAuthenticateSuccess() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        when(userService.authenticate("testUser", "password")).thenReturn(true);

        ResponseEntity<String> response = userController.authenticate(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Authenticated", response.getBody());
    }

    @Test
    void testAuthenticateFailure() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("wrongPassword");

        when(userService.authenticate("testUser", "wrongPassword")).thenReturn(false);

        ResponseEntity<String> response = userController.authenticate(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }
}
