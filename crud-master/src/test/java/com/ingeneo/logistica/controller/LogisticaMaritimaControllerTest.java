package com.ingeneo.logistica.controller;

import com.ingeneo.logistica.dto.PlanEntregaMaritimaRequest;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.PlanEntregaMaritimaEntity;
import com.ingeneo.logistica.service.impl.PlanEntregaMaritimaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class LogisticaMaritimaControllerTest {

    @Mock
    private PlanEntregaMaritimaServiceImpl planEntregaMaritimaServiceImpl;

    @InjectMocks
    private LogisticaMaritimaController logisticaMaritimaController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSearchPlanesEntregaMaritima() {
        // Crear datos de prueba
        List<PlanEntregaMaritimaEntity> planesEntregaMaritima = new ArrayList<>();
        planesEntregaMaritima.add(new PlanEntregaMaritimaEntity());

        // Configurar comportamiento del servicio mock
        when(planEntregaMaritimaServiceImpl.searchPlanesEntregaMaritimo(anyString(), anyInt(), anyString(), anyString(), anyDouble(), anyDouble(), anyString(), anyString(), anyString(), anyString(), anyInt()))
                .thenReturn(planesEntregaMaritima);

        // Llamar al método del controlador
        List<PlanEntregaMaritimaEntity> result = logisticaMaritimaController.searchPlanesEntregaMaritima(anyString(), anyInt(), anyString(), anyString(), anyDouble(), anyDouble(), anyString(), anyString(), anyString(), anyString(), anyInt());

        // Verificar el resultado
        assertEquals(planesEntregaMaritima, result);
    }

    @Test
    void testObtenerPlanEntregaMaritimoPorId() throws GeneralException {
        // Crear datos de prueba
        PlanEntregaMaritimaEntity planEntregaMaritima = new PlanEntregaMaritimaEntity();
        String numeroGuia = "123";

        // Configurar comportamiento del servicio mock
        when(planEntregaMaritimaServiceImpl.obtenerPlanEntregaMaritimoPorId(eq(numeroGuia)))
                .thenReturn(ResponseEntity.ok(planEntregaMaritima));

        // Llamar al método del controlador
        ResponseEntity<PlanEntregaMaritimaEntity> result = logisticaMaritimaController.obtenerPlanEntregaMaritimoPorId(numeroGuia);

        // Verificar el resultado
        assertEquals(ResponseEntity.ok(planEntregaMaritima), result);
    }

    @Test
    void testGuardarPlanEntregaMaritimo() throws GeneralException {
        // Crear datos de prueba
        PlanEntregaMaritimaRequest planEntregaMaritimaRequest = new PlanEntregaMaritimaRequest();
        PlanEntregaMaritimaEntity planEntregaMaritima = new PlanEntregaMaritimaEntity();

        // Configurar comportamiento del servicio mock
        when(planEntregaMaritimaServiceImpl.guardarPlanEntregaMaritimo(eq(planEntregaMaritimaRequest)))
                .thenReturn(ResponseEntity.ok(planEntregaMaritima));

        // Llamar al método del controlador
        ResponseEntity<PlanEntregaMaritimaEntity> result = logisticaMaritimaController.guardarPlanEntregaMaritimo(planEntregaMaritimaRequest);

        // Verificar el resultado
        assertEquals(ResponseEntity.ok(planEntregaMaritima), result);
    }

    @Test
    void testActualizarPlanEntregaMaritimo() throws GeneralException {
        // Crear datos de prueba
        PlanEntregaMaritimaRequest planEntregaMaritimaRequest = new PlanEntregaMaritimaRequest();
        PlanEntregaMaritimaEntity planEntregaMaritima = new PlanEntregaMaritimaEntity();
        String numeroGuia = "123";

        // Configurar comportamiento del servicio mock
        when(planEntregaMaritimaServiceImpl.actualizarPlanEntregaMaritimo(eq(planEntregaMaritimaRequest), eq(numeroGuia)))
                .thenReturn(ResponseEntity.ok(planEntregaMaritima));

        // Llamar al método del controlador
        ResponseEntity<PlanEntregaMaritimaEntity> result = logisticaMaritimaController.actualizarPlanEntregaMaritimo(planEntregaMaritimaRequest, numeroGuia);

        // Verificar el resultado
        assertEquals(ResponseEntity.ok(planEntregaMaritima), result);
    }

    @Test
    void testEliminarPlanEntregaMaritimo() throws GeneralException {
        // Crear datos de prueba
        PlanEntregaMaritimaEntity planEntregaMaritima = new PlanEntregaMaritimaEntity();
        String numeroGuia = "123";

        // Configurar comportamiento del servicio mock
        when(planEntregaMaritimaServiceImpl.eliminarPlanEntregaMaritimo(eq(numeroGuia)))
                .thenReturn(ResponseEntity.ok(planEntregaMaritima));

        // Llamar al método del controlador
        ResponseEntity<PlanEntregaMaritimaEntity> result = logisticaMaritimaController.eliminarPlanEntregaMaritimo(numeroGuia);

        // Verificar el resultado
        assertEquals(ResponseEntity.ok(planEntregaMaritima), result);
    }
}
