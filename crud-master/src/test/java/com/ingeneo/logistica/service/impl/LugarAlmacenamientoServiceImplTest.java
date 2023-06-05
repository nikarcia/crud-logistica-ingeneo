package com.ingeneo.logistica.service.impl;

import static org.junit.jupiter.api.Assertions.*;


import com.ingeneo.logistica.dto.LugarAlmacenamientoDto;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.Cliente;
import com.ingeneo.logistica.entity.LugarAlmacenamientoEntity;
import com.ingeneo.logistica.repository.ClienteRepository;
import com.ingeneo.logistica.repository.LugarAlmacenamientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LugarAlmacenamientoServiceImplTest {

    private LugarAlmacenamientoServiceImpl lugarAlmacenamientoService;

    @Mock
    private LugarAlmacenamientoRepository lugarAlmacenamientoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        lugarAlmacenamientoService = new LugarAlmacenamientoServiceImpl();
        lugarAlmacenamientoService.setLugarAlmacenamientoRepository(lugarAlmacenamientoRepository);
        lugarAlmacenamientoService.setClienteRepository(clienteRepository);
    }

    @Test
    void listarLugaresAlmacenamiento_DebeRetornarListaDeLugaresAlmacenamientoDto() {
        // Arrange
        List<LugarAlmacenamientoEntity> lugaresAlmacenamientoEntity = Arrays.asList(
                new LugarAlmacenamientoEntity(1, "Lugar 1", "Dirección 1", "Logística 1", new Cliente()),
                new LugarAlmacenamientoEntity(2, "Lugar 2", "Dirección 2", "Logística 2", new Cliente()),
                new LugarAlmacenamientoEntity(3, "Lugar 3", "Dirección 3", "Logística 3", new Cliente())
        );
        when(lugarAlmacenamientoRepository.findAll()).thenReturn(lugaresAlmacenamientoEntity);

        // Act
        List<LugarAlmacenamientoDto> lugaresAlmacenamientoDto = lugarAlmacenamientoService.listarLugaresAlmacenamiento();

        // Assert
        assertEquals(lugaresAlmacenamientoEntity.size(), lugaresAlmacenamientoDto.size());
        verify(lugarAlmacenamientoRepository, times(1)).findAll();
    }

    @Test
    void getLugaresAlmacenamientoByCliente_ClienteExistente_DebeRetornarListaDeLugaresAlmacenamientoEntity() throws GeneralException {
        // Arrange
        Integer clienteId = 1;
        Cliente cliente = new Cliente(clienteId, "Cliente 1");
        List<LugarAlmacenamientoEntity> lugaresAlmacenamientoEntity = Arrays.asList(
                new LugarAlmacenamientoEntity(1, "Lugar 1", "Dirección 1", "Logística 1", cliente),
                new LugarAlmacenamientoEntity(2, "Lugar 2", "Dirección 2", "Logística 2", cliente)
        );
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(lugarAlmacenamientoRepository.findByCliente(cliente)).thenReturn(lugaresAlmacenamientoEntity);

        // Act
        List<LugarAlmacenamientoEntity> lugaresAlmacenamientoObtenidos = lugarAlmacenamientoService.getLugaresAlmacenamientoByCliente(clienteId);

        // Assert
        assertEquals(lugaresAlmacenamientoEntity, lugaresAlmacenamientoObtenidos);
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(lugarAlmacenamientoRepository, times(1)).findByCliente(cliente);
    }

    @Test
    void getLugaresAlmacenamientoByCliente_ClienteNoExistente_DebeLanzarExcepcion() {
        // Arrange
        Integer clienteId = 1;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Act
        GeneralException exception = assertThrows(GeneralException.class, () -> lugarAlmacenamientoService.getLugaresAlmacenamientoByCliente(clienteId));

        // Assert
        assertEquals("cliente  " + clienteId + " No existe en db", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(lugarAlmacenamientoRepository, never()).findByCliente(any());
    }

    @Test
    void getLugaresAlmacenamientoByClienteAndLogistica_ClienteExistente_DebeRetornarListaDeLugaresAlmacenamientoDto() throws GeneralException {
        // Arrange
        Integer clienteId = 1;
        String logistica = "Logística";
        Cliente cliente = new Cliente(clienteId, "Cliente 1");
        List<LugarAlmacenamientoEntity> lugaresAlmacenamientoEntity = Arrays.asList(
                new LugarAlmacenamientoEntity(1, "Lugar 1", "Dirección 1", logistica, cliente),
                new LugarAlmacenamientoEntity(2, "Lugar 2", "Dirección 2", logistica, cliente)
        );
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(lugarAlmacenamientoRepository.findByClienteAndLogistica(cliente, logistica)).thenReturn(lugaresAlmacenamientoEntity);

        // Act
        List<LugarAlmacenamientoDto> lugaresAlmacenamientoDto = lugarAlmacenamientoService.getLugaresAlmacenamientoByClienteAndLogistica(clienteId, logistica);

        // Assert
        assertEquals(lugaresAlmacenamientoEntity.size(), lugaresAlmacenamientoDto.size());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(lugarAlmacenamientoRepository, times(1)).findByClienteAndLogistica(cliente, logistica);
    }

    @Test
    void guardarLugarAlmacenamiento_LugarAlmacenamientoDtoValido_DebeRetornarLugarAlmacenamientoDto() throws GeneralException {
        // Arrange
        Integer clienteId = 1;
        String nombreLugar = "Lugar 1";
        String logistica = "Logística 1";
        LugarAlmacenamientoDto lugarAlmacenamientoDto = new LugarAlmacenamientoDto();
        lugarAlmacenamientoDto.setClienteId(clienteId);
        lugarAlmacenamientoDto.setNombre(nombreLugar);
        lugarAlmacenamientoDto.setLogistica(logistica);
        Cliente cliente = new Cliente(clienteId, "Cliente 1");
        LugarAlmacenamientoEntity lugarAlmacenamientoEntity = new LugarAlmacenamientoEntity(1, nombreLugar, "Dirección 1", logistica, cliente);
        when(lugarAlmacenamientoRepository.existsByNombre(nombreLugar)).thenReturn(false);
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(lugarAlmacenamientoRepository.save(any())).thenReturn(lugarAlmacenamientoEntity);

        // Act
        LugarAlmacenamientoDto lugarAlmacenamientoGuardado = lugarAlmacenamientoService.guardarLugarAlmacenamiento(lugarAlmacenamientoDto);

        // Assert
        assertEquals(nombreLugar, lugarAlmacenamientoGuardado.getNombre());
        assertEquals(clienteId, lugarAlmacenamientoGuardado.getClienteId());
        assertEquals(logistica, lugarAlmacenamientoGuardado.getLogistica());
        verify(lugarAlmacenamientoRepository, times(1)).existsByNombre(nombreLugar);
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(lugarAlmacenamientoRepository, times(1)).save(any());
    }

    @Test
    void guardarLugarAlmacenamiento_LugarAlmacenamientoDtoConNombreExistente_DebeLanzarExcepcion() {
        // Arrange
        String nombreLugar = "Lugar 1";
        LugarAlmacenamientoDto lugarAlmacenamientoDto = new LugarAlmacenamientoDto();
        lugarAlmacenamientoDto.setNombre(nombreLugar);
        when(lugarAlmacenamientoRepository.existsByNombre(nombreLugar)).thenReturn(true);

        // Act
        GeneralException exception = assertThrows(GeneralException.class, () -> lugarAlmacenamientoService.guardarLugarAlmacenamiento(lugarAlmacenamientoDto));

        // Assert
        assertEquals("lugar de almacenamiento nombre:" + nombreLugar + " ya existe en db", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(lugarAlmacenamientoRepository, times(1)).existsByNombre(nombreLugar);
        verify(clienteRepository, never()).findById(any());
        verify(lugarAlmacenamientoRepository, never()).save(any());
    }

    @Test
    void guardarLugarAlmacenamiento_ClienteNoExistente_DebeLanzarExcepcion() {
        // Arrange
        Integer clienteId = 1;
        LugarAlmacenamientoDto lugarAlmacenamientoDto = new LugarAlmacenamientoDto();
        lugarAlmacenamientoDto.setClienteId(clienteId);
        when(lugarAlmacenamientoRepository.existsByNombre(any())).thenReturn(false);
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Act
        GeneralException exception = assertThrows(GeneralException.class, () -> lugarAlmacenamientoService.guardarLugarAlmacenamiento(lugarAlmacenamientoDto));

        // Assert
        assertEquals("cliente  " + clienteId + " No existe en db", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(lugarAlmacenamientoRepository, times(1)).existsByNombre(any());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(lugarAlmacenamientoRepository, never()).save(any());
    }

    @Test
    void actualizarLugarAlmacenamiento_LugarAlmacenamientoDtoValido_DebeRetornarLugarAlmacenamientoDto() throws GeneralException {
        // Arrange
        Integer lugarId = 1;
        Integer clienteId = 1;
        String nombreLugar = "Lugar 1";
        String logistica = "Logística 1";
        LugarAlmacenamientoDto lugarAlmacenamientoDto = new LugarAlmacenamientoDto();
        lugarAlmacenamientoDto.setClienteId(clienteId);
        lugarAlmacenamientoDto.setNombre(nombreLugar);
        lugarAlmacenamientoDto.setLogistica(logistica);
        Cliente cliente = new Cliente(clienteId, "Cliente 1");
        LugarAlmacenamientoEntity lugarAlmacenamientoEntity = new LugarAlmacenamientoEntity(lugarId, nombreLugar, "Dirección 1", logistica, cliente);
        when(lugarAlmacenamientoRepository.findById(lugarId)).thenReturn(Optional.of(lugarAlmacenamientoEntity));
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(lugarAlmacenamientoRepository.save(any())).thenReturn(lugarAlmacenamientoEntity);

        // Act
        LugarAlmacenamientoDto lugarAlmacenamientoActualizado = lugarAlmacenamientoService.actualizarLugarAlmacenamiento(lugarAlmacenamientoDto, lugarId);

        // Assert
        assertEquals(nombreLugar, lugarAlmacenamientoActualizado.getNombre());
        assertEquals(clienteId, lugarAlmacenamientoActualizado.getClienteId());
        assertEquals(logistica, lugarAlmacenamientoActualizado.getLogistica());
        verify(lugarAlmacenamientoRepository, times(1)).findById(lugarId);
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(lugarAlmacenamientoRepository, times(1)).save(any());
    }

    @Test
    void actualizarLugarAlmacenamiento_LugarAlmacenamientoDtoConIdNoExistente_DebeLanzarExcepcion() {
        // Arrange
        Integer lugarId = 1;
        LugarAlmacenamientoDto lugarAlmacenamientoDto = new LugarAlmacenamientoDto();
        lugarAlmacenamientoDto.setClienteId(1);
        when(lugarAlmacenamientoRepository.findById(lugarId)).thenReturn(Optional.empty());

        // Act
        GeneralException exception = assertThrows(GeneralException.class, () -> lugarAlmacenamientoService.actualizarLugarAlmacenamiento(lugarAlmacenamientoDto, lugarId));

        // Assert
        assertEquals("lugar de almacenamiento   " + lugarId + " No existe en db", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(lugarAlmacenamientoRepository, times(1)).findById(lugarId);
        verify(clienteRepository, never()).findById(any());
        verify(lugarAlmacenamientoRepository, never()).save(any());
    }

    @Test
    void actualizarLugarAlmacenamiento_ClienteNoExistente_DebeLanzarExcepcion() {
        // Arrange
        Integer lugarId = 1;
        Integer clienteId = 1;
        LugarAlmacenamientoDto lugarAlmacenamientoDto = new LugarAlmacenamientoDto();
        lugarAlmacenamientoDto.setClienteId(clienteId);
        when(lugarAlmacenamientoRepository.findById(lugarId)).thenReturn(Optional.of(new LugarAlmacenamientoEntity()));
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Act
        GeneralException exception = assertThrows(GeneralException.class, () -> lugarAlmacenamientoService.actualizarLugarAlmacenamiento(lugarAlmacenamientoDto, lugarId));

        // Assert
        assertEquals("cliente  " + clienteId + " No existe en db", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(lugarAlmacenamientoRepository, times(1)).findById(lugarId);
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(lugarAlmacenamientoRepository, never()).save(any());
    }

    @Test
    void eliminarLugarAlmacenamiento_LugarAlmacenamientoExistente_DebeEliminarLugarAlmacenamiento() throws GeneralException {
        // Arrange
        Integer lugarId = 1;
        LugarAlmacenamientoEntity lugarAlmacenamientoEntity = new LugarAlmacenamientoEntity(lugarId, "Lugar 1", "Dirección 1", "Logística 1", new Cliente());
        when(lugarAlmacenamientoRepository.findById(lugarId)).thenReturn(Optional.of(lugarAlmacenamientoEntity));

        // Act
        lugarAlmacenamientoService.eliminarLugarAlmacenamiento(lugarId);

        // Assert
        verify(lugarAlmacenamientoRepository, times(1)).findById(lugarId);
        verify(lugarAlmacenamientoRepository, times(1)).deleteById(lugarId);
    }

    @Test
    void eliminarLugarAlmacenamiento_LugarAlmacenamientoNoExistente_DebeLanzarExcepcion() {
        // Arrange
        Integer lugarId = 1;
        when(lugarAlmacenamientoRepository.findById(lugarId)).thenReturn(Optional.empty());

        // Act
        GeneralException exception = assertThrows(GeneralException.class, () -> lugarAlmacenamientoService.eliminarLugarAlmacenamiento(lugarId));

        // Assert
        assertEquals("lugar de almacenamiento   " + lugarId + " No existe en db", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(lugarAlmacenamientoRepository, times(1)).findById(lugarId);
        verify(lugarAlmacenamientoRepository, never()).deleteById(any());
    }

    @Test
    void obtenerLugarAlmacenamientoPorId_LugarAlmacenamientoExistente_DebeRetornarLugarAlmacenamientoDto() throws GeneralException {
        // Arrange
        Integer lugarId = 1;
        LugarAlmacenamientoEntity lugarAlmacenamientoEntity = new LugarAlmacenamientoEntity(lugarId, "Lugar 1", "Dirección 1", "Logística 1", new Cliente());
        when(lugarAlmacenamientoRepository.findById(lugarId)).thenReturn(Optional.of(lugarAlmacenamientoEntity));

        // Act
        LugarAlmacenamientoDto lugarAlmacenamientoDto = lugarAlmacenamientoService.obtenerLugarAlmacenamientoPorId(lugarId);

        // Assert
        assertNotNull(lugarAlmacenamientoDto);
        assertEquals(lugarId, lugarAlmacenamientoDto.getId());
        verify(lugarAlmacenamientoRepository, times(1)).findById(lugarId);
    }

    @Test
    void obtenerLugarAlmacenamientoPorId_LugarAlmacenamientoNoExistente_DebeLanzarExcepcion() {
        // Arrange
        Integer lugarId = 1;
        when(lugarAlmacenamientoRepository.findById(lugarId)).thenReturn(Optional.empty());

        // Act
        GeneralException exception = assertThrows(GeneralException.class, () -> lugarAlmacenamientoService.obtenerLugarAlmacenamientoPorId(lugarId));

        // Assert
        assertEquals("lugar de almacenamiento   " + lugarId + " No existe en db", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(lugarAlmacenamientoRepository, times(1)).findById(lugarId);
    }



}
