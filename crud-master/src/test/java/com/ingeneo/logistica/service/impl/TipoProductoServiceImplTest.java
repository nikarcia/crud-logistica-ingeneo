package com.ingeneo.logistica.service.impl;

import com.ingeneo.logistica.dto.TipoProductoDto;
import com.ingeneo.logistica.entity.Cliente;
import com.ingeneo.logistica.entity.TipoProductoEntity;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.repository.ClienteRepository;
import com.ingeneo.logistica.repository.TipoProductoRepository;
import com.ingeneo.logistica.service.impl.TipoProductoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class TipoProductoServiceImplTest {

    private TipoProductoServiceImpl tipoProductoService;

    @Mock
    private TipoProductoRepository tipoProductoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tipoProductoService = new TipoProductoServiceImpl();
        tipoProductoService.setTipoProductoRepository(tipoProductoRepository);
        tipoProductoService.setClienteRepository(clienteRepository);
    }

    @Test
    void listarTipoProductos() {
        // Crear datos de prueba
        TipoProductoEntity tipoProductoEntity1 = new TipoProductoEntity();
        tipoProductoEntity1.setId(1);
        tipoProductoEntity1.setNombre("Producto 1");
        tipoProductoEntity1.setLogistica("Logística 1");
        Cliente cliente1 = new Cliente();
        cliente1.setId(1);
        tipoProductoEntity1.setCliente(cliente1);

        TipoProductoEntity tipoProductoEntity2 = new TipoProductoEntity();
        tipoProductoEntity2.setId(2);
        tipoProductoEntity2.setNombre("Producto 2");
        tipoProductoEntity2.setLogistica("Logística 2");
        Cliente cliente2 = new Cliente();
        cliente2.setId(2);
        tipoProductoEntity2.setCliente(cliente2);

        List<TipoProductoEntity> tipoProductoEntities = new ArrayList<>();
        tipoProductoEntities.add(tipoProductoEntity1);
        tipoProductoEntities.add(tipoProductoEntity2);

        // Mock del repositorio
        when(tipoProductoRepository.findAll()).thenReturn(tipoProductoEntities);

        // Llamar al método a probar
        List<TipoProductoDto> tipoProductoDtos = tipoProductoService.listarTipoProductos();

        // Verificar los resultados
        assertEquals(2, tipoProductoDtos.size());
        assertEquals("Producto 1", tipoProductoDtos.get(0).getNombre());
        assertEquals("Logística 1", tipoProductoDtos.get(0).getLogistica());
        assertEquals(1, tipoProductoDtos.get(0).getClienteId());
        assertEquals("Producto 2", tipoProductoDtos.get(1).getNombre());
        assertEquals("Logística 2", tipoProductoDtos.get(1).getLogistica());
        assertEquals(2, tipoProductoDtos.get(1).getClienteId());
    }

    @Test
    void getProductosByCliente_existingClienteId_shouldReturnProductos() throws GeneralException {
        // Crear datos de prueba
        int clienteId = 1;
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        List<TipoProductoEntity> tipoProductoEntities = new ArrayList<>();
        tipoProductoEntities.add(new TipoProductoEntity());
        tipoProductoEntities.add(new TipoProductoEntity());

        // Mock del repositorio y del cliente existente
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(tipoProductoRepository.findByCliente(cliente)).thenReturn(tipoProductoEntities);

        // Llamar al método a probar
        List<TipoProductoEntity> result = tipoProductoService.getProductosByCliente(clienteId);

        // Verificar los resultados
        assertEquals(2, result.size());
    }

    @Test
    void getProductosByCliente_nonExistingClienteId_shouldThrowException() {
        // Crear datos de prueba
        int clienteId = 1;

        // Mock del cliente no existente
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Verificar que se lance una excepción
        assertThrows(GeneralException.class, () -> {
            tipoProductoService.getProductosByCliente(clienteId);
        });
    }

    @Test
    void getProductosByClienteAndLogistica_existingClienteIdAndLogistica_shouldReturnProductos() throws GeneralException {
        // Crear datos de prueba
        int clienteId = 1;
        String logistica = "Logística";
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        List<TipoProductoEntity> tipoProductoEntities = new ArrayList<>();
        tipoProductoEntities.add(new TipoProductoEntity());
        tipoProductoEntities.add(new TipoProductoEntity());

        // Mock del repositorio y del cliente existente
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(tipoProductoRepository.findByClienteAndLogistica(cliente, logistica)).thenReturn(tipoProductoEntities);

        // Llamar al método a probar
        List<TipoProductoEntity> result = tipoProductoService.getProductosByClienteAndLogistica(clienteId, logistica);

        // Verificar los resultados
        assertEquals(2, result.size());
    }

    @Test
    void getProductosByClienteAndLogistica_nonExistingClienteId_shouldThrowException() {
        // Crear datos de prueba
        int clienteId = 1;
        String logistica = "Logística";

        // Mock del cliente no existente
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Verificar que se lance una excepción
        assertThrows(GeneralException.class, () -> {
            tipoProductoService.getProductosByClienteAndLogistica(clienteId, logistica);
        });
    }

    // Realiza pruebas similares para los demás métodos de la clase TipoProductoServiceImpl
    // ...

    // Métodos auxiliares para inyectar los mocks en el servicio

    private void setTipoProductoRepository(TipoProductoRepository tipoProductoRepository) {
        this.tipoProductoRepository = tipoProductoRepository;
    }

    private void setClienteRepository(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
}
