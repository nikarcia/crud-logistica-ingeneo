package com.ingeneo.logistica.controller;

import com.ingeneo.logistica.dto.TipoProductoDto;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.TipoProductoEntity;
import com.ingeneo.logistica.service.impl.TipoProductoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class TipoProductoControllerTest {

    @Mock
    private TipoProductoServiceImpl tipoProductoServiceImpl;

    @InjectMocks
    private TipoProductoController tipoProductoController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testListarTipoProductoEntitys() {
        // Crear datos de prueba
        List<TipoProductoDto> tipoProductoDtos = new ArrayList<>();
        tipoProductoDtos.add(new TipoProductoDto());

        // Configurar comportamiento del servicio mock
        when(tipoProductoServiceImpl.listarTipoProductos()).thenReturn(tipoProductoDtos);

        // Llamar al método del controlador
        ResponseEntity<List<TipoProductoDto>> result = tipoProductoController.listarTipoProductoEntitys();

        // Verificar el resultado
        assertEquals(ResponseEntity.ok(tipoProductoDtos), result);
    }

    @Test
    void testGetProductosByCliente() throws GeneralException {
        // Crear datos de prueba
        Integer clienteId = 1;
        List<TipoProductoEntity> tipoProductoEntities = new ArrayList<>();
        tipoProductoEntities.add(new TipoProductoEntity());

        // Configurar comportamiento del servicio mock
        when(tipoProductoServiceImpl.getProductosByCliente(eq(clienteId))).thenReturn(tipoProductoEntities);

        // Llamar al método del controlador
        ResponseEntity<List<TipoProductoEntity>> result = tipoProductoController.getProductosByCliente(clienteId);

        // Verificar el resultado
        assertEquals(ResponseEntity.ok(tipoProductoEntities), result);
    }

    @Test
    void testGetProductosByClienteAndLogistica() throws GeneralException {
        // Crear datos de prueba
        Integer clienteId = 1;
        String logistica = "logistica";
        List<TipoProductoEntity> tipoProductoEntities = new ArrayList<>();
        tipoProductoEntities.add(new TipoProductoEntity());

        // Configurar comportamiento del servicio mock
        when(tipoProductoServiceImpl.getProductosByClienteAndLogistica(eq(clienteId), eq(logistica))).thenReturn(tipoProductoEntities);

        // Llamar al método del controlador
        ResponseEntity<List<TipoProductoEntity>> result = tipoProductoController.getProductosByClienteAndLogistica(clienteId, logistica);

        // Verificar el resultado
        assertEquals(ResponseEntity.ok(tipoProductoEntities), result);
    }

    @Test
    void testGuardarTipoProductoEntity() throws GeneralException {
        // Crear datos de prueba
        TipoProductoDto tipoProductoDto = new TipoProductoDto();
        TipoProductoEntity tipoProductoEntityGuardado = new TipoProductoEntity();

        // Configurar comportamiento del servicio mock
        when(tipoProductoServiceImpl.guardarTipoProducto(eq(tipoProductoDto))).thenReturn(tipoProductoEntityGuardado);

        // Llamar al método del controlador
        ResponseEntity<TipoProductoEntity> result = tipoProductoController.guardarTipoProductoEntity(tipoProductoDto);

        // Verificar el resultado
        assertEquals(new ResponseEntity<>(tipoProductoEntityGuardado, HttpStatus.CREATED), result);
    }

    @Test
    void testActualizarTipoProductoEntity() throws GeneralException {
        // Crear datos de prueba
        TipoProductoDto tipoProductoDto = new TipoProductoDto();
        Integer id = 1;
        TipoProductoEntity tipoProductoEntityActualizado = new TipoProductoEntity();

        // Configurar comportamiento del servicio mock
        when(tipoProductoServiceImpl.actualizarTipoProducto(eq(id), eq(tipoProductoDto))).thenReturn(tipoProductoEntityActualizado);

        // Llamar al método del controlador
        ResponseEntity<TipoProductoEntity> result = tipoProductoController.actualizarTipoProductoEntity(tipoProductoDto, id);

        // Verificar el resultado
        assertEquals(ResponseEntity.noContent().build(), result);
    }

    @Test
    void testEliminarTipoProductoEntity() throws GeneralException {
        // Crear datos de prueba
        Integer id = 1;

        // Configurar comportamiento del servicio mock
        when(tipoProductoServiceImpl.eliminarTipoProducto(eq(id))).thenReturn(true);

        // Llamar al método del controlador
        ResponseEntity<Void> result = tipoProductoController.eliminarTipoProductoEntity(id);

        // Verificar el resultado
        assertEquals(ResponseEntity.noContent().build(), result);
    }

    @Test
    void testObtenerTipoProductoEntityPorId() {
        // Crear datos de prueba
        Integer id = 1;
        TipoProductoEntity tipoProductoEntity = new TipoProductoEntity();

        // Configurar comportamiento del servicio mock
        when(tipoProductoServiceImpl.obtenerTipoProductoPorId(eq(id))).thenReturn(tipoProductoEntity);

        // Llamar al método del controlador
        ResponseEntity<TipoProductoEntity> result = tipoProductoController.obtenerTipoProductoEntityPorId(id);

        // Verificar el resultado
        assertEquals(ResponseEntity.ok(tipoProductoEntity), result);
    }
}
