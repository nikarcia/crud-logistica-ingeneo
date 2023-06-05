package com.ingeneo.logistica.controller;

import com.ingeneo.logistica.dto.LugarAlmacenamientoDto;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.LugarAlmacenamientoEntity;
import com.ingeneo.logistica.service.LugarAlmacenamientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class LugarAlmacenamientoControllerTest {

    @Mock
    private LugarAlmacenamientoService lugarAlmacenamientoService;

    @InjectMocks
    private LugarAlmacenamientoController lugarAlmacenamientoController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testListarLugaresAlmacenamiento() {
        // Crear datos de prueba
        List<LugarAlmacenamientoDto> lugaresAlmacenamientoDto = new ArrayList<>();
        lugaresAlmacenamientoDto.add(new LugarAlmacenamientoDto());

        // Configurar comportamiento del servicio mock
        when(lugarAlmacenamientoService.listarLugaresAlmacenamiento()).thenReturn(lugaresAlmacenamientoDto);

        // Llamar al método del controlador
        ResponseEntity<List<LugarAlmacenamientoDto>> result = lugarAlmacenamientoController.listarLugaresAlmacenamiento();

        // Verificar el resultado
        assertEquals(ResponseEntity.ok(lugaresAlmacenamientoDto), result);
    }

    @Test
    void testGetLugaresAlmacenamientoByCliente() throws GeneralException {
        // Crear datos de prueba
        Integer clienteId = 1;
        List<LugarAlmacenamientoEntity> lugarAlmacenamientoEntities = new ArrayList<>();
        lugarAlmacenamientoEntities.add(new LugarAlmacenamientoEntity());

        // Configurar comportamiento del servicio mock
        when(lugarAlmacenamientoService.getLugaresAlmacenamientoByCliente(eq(clienteId))).thenReturn(lugarAlmacenamientoEntities);

        // Llamar al método del controlador
        ResponseEntity<List<LugarAlmacenamientoEntity>> result = lugarAlmacenamientoController.getLugaresAlmacenamientoByCliente(clienteId);

        // Verificar el resultado
        assertEquals(ResponseEntity.ok(lugarAlmacenamientoEntities), result);
    }

    @Test
    void testGetLugaresAlmacenamientoByClienteAndLogistica() throws GeneralException {
        // Crear datos de prueba
        Integer clienteId = 1;
        String logistica = "logistica";
        List<LugarAlmacenamientoDto> lugaresAlmacenamientoDto = new ArrayList<>();
        lugaresAlmacenamientoDto.add(new LugarAlmacenamientoDto());

        // Configurar comportamiento del servicio mock
        when(lugarAlmacenamientoService.getLugaresAlmacenamientoByClienteAndLogistica(eq(clienteId), eq(logistica))).thenReturn(lugaresAlmacenamientoDto);

        // Llamar al método del controlador
        ResponseEntity<List<LugarAlmacenamientoDto>> result = lugarAlmacenamientoController.getLugaresAlmacenamientoByClienteAndLogistica(clienteId, logistica);

        // Verificar el resultado
        assertEquals(ResponseEntity.ok(lugaresAlmacenamientoDto), result);
    }

    @Test
    void testGuardarLugarAlmacenamiento() throws GeneralException {
        // Crear datos de prueba
        LugarAlmacenamientoDto lugarAlmacenamientoDto = new LugarAlmacenamientoDto();

        // Configurar comportamiento del servicio mock
        when(lugarAlmacenamientoService.guardarLugarAlmacenamiento(eq(lugarAlmacenamientoDto))).thenReturn(lugarAlmacenamientoDto);

        // Llamar al método del controlador
        ResponseEntity<LugarAlmacenamientoDto> result = lugarAlmacenamientoController.guardarLugarAlmacenamiento(lugarAlmacenamientoDto);

        // Verificar el resultado
        assertEquals(new ResponseEntity<>(lugarAlmacenamientoDto, HttpStatus.CREATED), result);
    }

    @Test
    void testActualizarLugarAlmacenamiento() throws GeneralException {
        // Crear datos de prueba
        LugarAlmacenamientoDto lugarAlmacenamientoDto = new LugarAlmacenamientoDto();
        Integer id = 1;

        // Configurar comportamiento del servicio mock
        when(lugarAlmacenamientoService.actualizarLugarAlmacenamiento(eq(lugarAlmacenamientoDto), eq(id))).thenReturn(lugarAlmacenamientoDto);

        // Llamar al método del controlador
        ResponseEntity<LugarAlmacenamientoDto> result = lugarAlmacenamientoController.actualizarLugarAlmacenamiento(lugarAlmacenamientoDto, id);

        // Verificar el resultado
        assertEquals(ResponseEntity.ok(lugarAlmacenamientoDto), result);
    }

    @Test
    void testEliminarLugarAlmacenamiento() throws GeneralException {
        // Crear datos de prueba
        Integer id = 1;

        // Llamar al método del controlador
        ResponseEntity<Void> result = lugarAlmacenamientoController.eliminarLugarAlmacenamiento(id);

        // Verificar el resultado
        assertEquals(ResponseEntity.noContent().build(), result);
    }

    @Test
    void testObtenerLugarAlmacenamientoPorId() throws GeneralException {
        // Crear datos de prueba
        Integer id = 1;
        LugarAlmacenamientoDto lugarAlmacenamientoDto = new LugarAlmacenamientoDto();

        // Configurar comportamiento del servicio mock
        when(lugarAlmacenamientoService.obtenerLugarAlmacenamientoPorId(eq(id))).thenReturn(lugarAlmacenamientoDto);

        // Llamar al método del controlador
        ResponseEntity<LugarAlmacenamientoDto> result = lugarAlmacenamientoController.obtenerLugarAlmacenamientoPorId(id);

        // Verificar el resultado
        assertEquals(ResponseEntity.ok(lugarAlmacenamientoDto), result);
    }
}
