package com.ingeneo.logistica.service.impl;

import com.ingeneo.logistica.dto.PlanEntregaTerrestreRequest;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.*;
import com.ingeneo.logistica.repository.ClienteRepository;
import com.ingeneo.logistica.repository.LugarAlmacenamientoRepository;
import com.ingeneo.logistica.repository.PlanEntregaTerrestreRepository;
import com.ingeneo.logistica.repository.TipoProductoRepository;
import com.ingeneo.logistica.service.PlanEntregaTerrestreService;
import com.ingeneo.logistica.service.impl.PlanEntregaTerrestreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlanEntregaTerrestreServiceImplTest {
    @Mock
    private PlanEntregaTerrestreRepository planEntregaTerrestreRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private TipoProductoRepository tipoProductoRepository;

    @Mock
    private LugarAlmacenamientoRepository lugarAlmacenamientoRepository;

    private PlanEntregaTerrestreService planEntregaTerrestreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        planEntregaTerrestreService = new PlanEntregaTerrestreServiceImpl(
                planEntregaTerrestreRepository,
                clienteRepository,
                tipoProductoRepository,
                lugarAlmacenamientoRepository
        );
    }



    @Test
    void guardarPlanEntregaTerrestre_nonExistingCliente_shouldThrowException() {
        // Crear datos de prueba
        PlanEntregaTerrestreRequest request = new PlanEntregaTerrestreRequest();
        request.setClienteId(1);
        // Configurar el resto de los campos necesarios en el request

        // Configurar el mock del repositorio para que devuelva un Optional vacío
        when(clienteRepository.findById(request.getClienteId())).thenReturn(Optional.empty());

        // Llamar al método a probar y verificar que lanza la excepción esperada
        assertThrows(GeneralException.class, () -> planEntregaTerrestreService.guardarPlanEntregaTerrestre(request));
    }





    @Test
    void guardarPlanEntregaTerrestre_invalidTipoProducto_shouldThrowException() {
        // Crear datos de prueba
        PlanEntregaTerrestreRequest request = new PlanEntregaTerrestreRequest();
        request.setClienteId(1);
        request.setTipoProducto(1);
        request.setBodegaEntrega(1);
        // Agregar el resto de los campos necesarios en el request

        Cliente cliente = new Cliente();
        cliente.setId(1);

        // Configurar el mock del repositorio del tipoProductoRepository para que devuelva un Optional vacío
        when(tipoProductoRepository.findByIdAndClienteAndLogistica(request.getTipoProducto(), cliente, "TERRESTRE")).thenReturn(Optional.empty());

        // Llamar al método a probar y verificar que lanza la excepción esperada
        assertThrows(GeneralException.class, () -> planEntregaTerrestreService.guardarPlanEntregaTerrestre(request));
    }

    @Test
    void guardarPlanEntregaTerrestre_invalidBodegaEntrega_shouldThrowException() {
        // Crear datos de prueba
        PlanEntregaTerrestreRequest request = new PlanEntregaTerrestreRequest();
        request.setClienteId(1);
        request.setTipoProducto(1);
        request.setBodegaEntrega(1);
        // Agregar el resto de los campos necesarios en el request

        Cliente cliente = new Cliente();
        cliente.setId(1);

        TipoProductoEntity tipoProductoEntity = new TipoProductoEntity();
        // Configurar el tipoProductoEntity según tus necesidades

        // Configurar el mock del repositorio del lugarAlmacenamientoRepository para que devuelva un Optional vacío
        when(lugarAlmacenamientoRepository.findByIdAndClienteAndLogistica(request.getBodegaEntrega(), cliente, "TERRESTRE")).thenReturn(Optional.empty());

        // Llamar al método a probar y verificar que lanza la excepción esperada
        assertThrows(GeneralException.class, () -> planEntregaTerrestreService.guardarPlanEntregaTerrestre(request));
    }

    @Test
    void buscarPlanEntregaTerrestre_existingPlanEntregaTerrestreId_shouldReturnPlanEntregaTerrestre() throws GeneralException {
        // Crear datos de prueba
        String planEntregaTerrestreId = "1";
        PlanEntregaTerrestreEntity planEntregaTerrestreEntity = new PlanEntregaTerrestreEntity();
        // Configurar el planEntregaTerrestreEntity según tus necesidades

        // Configurar el mock del repositorio para que devuelva el planEntregaTerrestreEntity cuando se llame al método findById
        when(planEntregaTerrestreRepository.findById(planEntregaTerrestreId)).thenReturn(Optional.of(planEntregaTerrestreEntity));

        // Llamar al método a probar
        ResponseEntity<PlanEntregaTerrestreEntity> response = planEntregaTerrestreService.obtenerPlanEntregaTerrestrePorId(planEntregaTerrestreId);

        // Verificar los resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        // Verificar el contenido del objeto PlanEntregaTerrestreEntity retornado según tus necesidades
    }

    @Test
    void buscarPlanEntregaTerrestre_nonExistingPlanEntregaTerrestreId_shouldThrowException() {
        // Crear datos de prueba
        String planEntregaTerrestreId = "1";

        // Configurar el mock del repositorio para que devuelva un Optional vacío cuando se llame al método findById
        when(planEntregaTerrestreRepository.findById(planEntregaTerrestreId)).thenReturn(Optional.empty());

        // Llamar al método a probar y verificar que lanza la excepción esperada
        assertThrows(GeneralException.class, () -> planEntregaTerrestreService.obtenerPlanEntregaTerrestrePorId(planEntregaTerrestreId));
    }

}
