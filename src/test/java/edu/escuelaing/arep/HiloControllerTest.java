package edu.escuelaing.arep;

import edu.escuelaing.arep.controller.HiloController;
import edu.escuelaing.arep.model.Hilo;
import edu.escuelaing.arep.service.HiloService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HiloControllerTest {

    @Mock
    private HiloService hiloService;

    @InjectMocks
    private HiloController hiloController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllHilos() {
        List<Hilo> mockHilos = Arrays.asList(new Hilo("Noticias"), new Hilo("Deportes"));
        when(hiloService.getAllHilos()).thenReturn(mockHilos);

        ResponseEntity<List<Hilo>> response = hiloController.getAllHilos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockHilos, response.getBody());
        verify(hiloService, times(1)).getAllHilos();
    }
}
