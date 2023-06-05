package com.ingeneo.logistica.service.impl;

import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.Cliente;
import com.ingeneo.logistica.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    private ClienteServiceImpl clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        clienteService = new ClienteServiceImpl(clienteRepository);
    }

    @Test
    void listarClientes_DebeRetornarListaDeClientes() {
        // Arrange
        List<Cliente> clientesEsperados = Arrays.asList(
                new Cliente(1, "Cliente 1"),
                new Cliente(2, "Cliente 2"),
                new Cliente(3, "Cliente 3")
        );
        when(clienteRepository.findAll()).thenReturn(clientesEsperados);

        // Act
        List<Cliente> clientesObtenidos = clienteService.listarClientes();

        // Assert
        assertEquals(clientesEsperados, clientesObtenidos);
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void guardarCliente_ClienteNoExisteEnDb_DebeGuardarCliente() throws GeneralException {
        // Arrange
        Cliente cliente = new Cliente(1, "Nuevo Cliente");
        when(clienteRepository.existsByNombre(cliente.getNombre())).thenReturn(false);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Act
        Cliente clienteGuardado = clienteService.guardarCliente(cliente);

        // Assert
        assertEquals(cliente, clienteGuardado);
        verify(clienteRepository, times(1)).existsByNombre(cliente.getNombre());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void guardarCliente_ClienteYaExisteEnDb_DebeLanzarExcepcion() {
        // Arrange
        Cliente cliente = new Cliente(1, "Cliente Existente");
        when(clienteRepository.existsByNombre(cliente.getNombre())).thenReturn(true);

        // Act & Assert
        assertThrows(GeneralException.class, () -> clienteService.guardarCliente(cliente));
        verify(clienteRepository, times(1)).existsByNombre(cliente.getNombre());
        verify(clienteRepository, never()).save(cliente);
    }

    @Test
    void actualizarCliente_ClienteExistente_DebeActualizarCliente() throws GeneralException {
        // Arrange
        Integer id = 1;
        Cliente clienteExistente = new Cliente(id, "Cliente Existente");
        Cliente clienteActualizado = new Cliente(id, "Cliente Actualizado");
        Optional<Cliente> clienteOptional = Optional.of(clienteExistente);
        when(clienteRepository.findById(id)).thenReturn(clienteOptional);
        when(clienteRepository.save(clienteActualizado)).thenReturn(clienteActualizado);

        // Act
        Cliente clienteActualizadoResultado = clienteService.actualizarCliente(id, clienteActualizado);

        // Assert
        assertEquals(clienteActualizado, clienteActualizadoResultado);
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, times(1)).save(clienteActualizado);
    }

    @Test
    void actualizarCliente_ClienteNoExisteEnDb_DebeLanzarExcepcion() {
        // Arrange
        Integer id = 1;
        Cliente cliente = new Cliente(id, "Cliente Actualizado");
        Optional<Cliente> clienteOptional = Optional.empty();
        when(clienteRepository.findById(id)).thenReturn(clienteOptional);

        // Act & Assert
        assertThrows(GeneralException.class, () -> clienteService.actualizarCliente(id, cliente));
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, never()).save(cliente);
    }

    @Test
    void eliminarCliente_ClienteExistente_DebeEliminarCliente() throws GeneralException {
        // Arrange
        Integer id = 1;
        Cliente clienteExistente = new Cliente(id, "Cliente Existente");
        Optional<Cliente> clienteOptional = Optional.of(clienteExistente);
        when(clienteRepository.findById(id)).thenReturn(clienteOptional);

        // Act
        boolean resultado = clienteService.eliminarCliente(id);

        // Assert
        assertTrue(resultado);
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, times(1)).delete(clienteExistente);
    }

    @Test
    void eliminarCliente_ClienteNoExisteEnDb_DebeLanzarExcepcion() {
        // Arrange
        Integer id = 1;
        Optional<Cliente> clienteOptional = Optional.empty();
        when(clienteRepository.findById(id)).thenReturn(clienteOptional);

        // Act & Assert
        assertThrows(GeneralException.class, () -> clienteService.eliminarCliente(id));
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, never()).delete(any());
    }

    @Test
    void obtenerClientePorId_ClienteExistente_DebeRetornarCliente() throws GeneralException {
        // Arrange
        Integer id = 1;
        Cliente clienteExistente = new Cliente(id, "Cliente Existente");
        Optional<Cliente> clienteOptional = Optional.of(clienteExistente);
        when(clienteRepository.findById(id)).thenReturn(clienteOptional);

        // Act
        Cliente clienteObtenido = clienteService.obtenerClientePorId(id);

        // Assert
        assertEquals(clienteExistente, clienteObtenido);
        verify(clienteRepository, times(1)).findById(id);
    }

    @Test
    void obtenerClientePorId_ClienteNoExisteEnDb_DebeLanzarExcepcion() {
        // Arrange
        Integer id = 1;
        Optional<Cliente> clienteOptional = Optional.empty();
        when(clienteRepository.findById(id)).thenReturn(clienteOptional);

        // Act & Assert
        assertThrows(GeneralException.class, () -> clienteService.obtenerClientePorId(id));
        verify(clienteRepository, times(1)).findById(id);
    }
}
