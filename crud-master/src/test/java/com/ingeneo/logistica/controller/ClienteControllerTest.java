package com.ingeneo.logistica.controller;

import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.Cliente;
import com.ingeneo.logistica.service.impl.ClienteServiceImpl;
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

class ClienteControllerTest {

    @Mock
    private ClienteServiceImpl clienteServiceImpl;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testListarClientes() {
        // Crear datos de prueba
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente());

        // Configurar comportamiento del servicio mock
        when(clienteServiceImpl.listarClientes()).thenReturn(clientes);

        // Llamar al método del controlador
        ResponseEntity<List<Cliente>> result = clienteController.listarClientes();

        // Verificar el resultado
        assertEquals(ResponseEntity.ok(clientes), result);
    }

    @Test
    void testGuardarCliente() throws GeneralException {
        // Crear datos de prueba
        Cliente cliente = new Cliente();
        Cliente clienteGuardado = new Cliente();
        clienteGuardado.setId(1);

        // Configurar comportamiento del servicio mock
        when(clienteServiceImpl.guardarCliente(eq(cliente))).thenReturn(clienteGuardado);

        // Llamar al método del controlador
        ResponseEntity<Cliente> result = clienteController.guardarCliente(cliente);

        // Verificar el resultado
        assertEquals(new ResponseEntity<>(clienteGuardado,HttpStatus.CREATED), result);
    }

    @Test
    void testActualizarCliente() throws GeneralException {
        // Crear datos de prueba
        Integer id = 1;
        Cliente cliente = new Cliente();
        Cliente clienteActualizado = new Cliente();

        // Configurar comportamiento del servicio mock
        when(clienteServiceImpl.actualizarCliente(eq(id), eq(cliente))).thenReturn(clienteActualizado);

        // Llamar al método del controlador
        ResponseEntity<Cliente> result = clienteController.actualizarCliente(id, cliente);

        // Verificar el resultado
        assertEquals(ResponseEntity.noContent().build(), result);
    }

    @Test
    void testEliminarCliente() throws GeneralException {
        // Crear datos de prueba
        Integer id = 1;

        // Configurar comportamiento del servicio mock
        when(clienteServiceImpl.eliminarCliente(eq(id))).thenReturn(true);

        // Llamar al método del controlador
        ResponseEntity<Void> result = clienteController.eliminarCliente(id);

        // Verificar el resultado
        assertEquals(ResponseEntity.noContent().build(), result);
    }

    @Test
    void testObtenerClientePorId() throws GeneralException {
        // Crear datos de prueba
        Integer id = 1;
        Cliente cliente = new Cliente();

        // Configurar comportamiento del servicio mock
        when(clienteServiceImpl.obtenerClientePorId(eq(id))).thenReturn(cliente);

        // Llamar al método del controlador
        ResponseEntity<Cliente> result = clienteController.obtenerClientePorId(id);

        // Verificar el resultado
        assertEquals(ResponseEntity.ok(cliente), result);
    }
}
