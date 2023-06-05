package com.ingeneo.logistica.service.impl;

import com.ingeneo.logistica.dto.PlanEntregaMaritimaRequest;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.Cliente;
import com.ingeneo.logistica.repository.ClienteRepository;
import com.ingeneo.logistica.repository.LugarAlmacenamientoRepository;
import com.ingeneo.logistica.repository.PlanEntregaMaritimaRepository;
import com.ingeneo.logistica.repository.TipoProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PlanEntregaMaritimaServiceImplTest {

    private PlanEntregaMaritimaRepository planEntregaMaritimaRepository;
    private ClienteRepository clienteRepository;
    private TipoProductoRepository tipoProductoRepository;
    private LugarAlmacenamientoRepository lugarAlmacenamientoRepository;
    private PlanEntregaMaritimaServiceImpl planEntregaMaritimaService;

    @BeforeEach
    void setup() {
        planEntregaMaritimaRepository = Mockito.mock(PlanEntregaMaritimaRepository.class);
        clienteRepository = Mockito.mock(ClienteRepository.class);
        tipoProductoRepository = Mockito.mock(TipoProductoRepository.class);
        lugarAlmacenamientoRepository = Mockito.mock(LugarAlmacenamientoRepository.class);
        planEntregaMaritimaService = new PlanEntregaMaritimaServiceImpl(
                planEntregaMaritimaRepository, clienteRepository,
                tipoProductoRepository, lugarAlmacenamientoRepository
        );
    }

    @Test
    void guardarPlanEntregaMaritimo_ClienteNoExiste_DebeLanzarGeneralException() {
        // Arrange
        PlanEntregaMaritimaRequest planEntregaMaritimaRequest = new PlanEntregaMaritimaRequest();
        planEntregaMaritimaRequest.setClienteId(1);

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        GeneralException exception = assertThrows(GeneralException.class,
                () -> planEntregaMaritimaService.guardarPlanEntregaMaritimo(planEntregaMaritimaRequest));

        assertEquals("cliente   1 No existe en db", exception.getMessage());
        verify(clienteRepository, times(1)).findById(1);
        verifyNoMoreInteractions(clienteRepository);
        verifyNoInteractions(tipoProductoRepository, lugarAlmacenamientoRepository, planEntregaMaritimaRepository);
    }

    @Test
    void guardarPlanEntregaMaritimo_TipoProductoNoExiste_DebeLanzarGeneralException() {
        // Arrange
        PlanEntregaMaritimaRequest planEntregaMaritimaRequest = new PlanEntregaMaritimaRequest();
        planEntregaMaritimaRequest.setClienteId(1);
        planEntregaMaritimaRequest.setTipoProducto(1);

        Cliente cliente = new Cliente();
        cliente.setId(1);

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));
        when(tipoProductoRepository.findByIdAndClienteAndLogistica(anyInt(), eq(cliente), eq("MARITIMA")))
                .thenReturn(Optional.empty());

        // Act & Assert
        GeneralException exception = assertThrows(GeneralException.class,
                () -> planEntregaMaritimaService.guardarPlanEntregaMaritimo(planEntregaMaritimaRequest));

        assertEquals("tipo de producto 1 NO esta relacionado al cliente o   No es de tipo MARITIMA",
                exception.getMessage());
        verify(clienteRepository, times(1)).findById(1);
        verify(tipoProductoRepository, times(1)).findByIdAndClienteAndLogistica(1, cliente, "MARITIMA");
        verifyNoMoreInteractions(clienteRepository, tipoProductoRepository);
        verifyNoInteractions(lugarAlmacenamientoRepository, planEntregaMaritimaRepository);
    }

    @Test
    void guardarPlanEntregaMaritimo_LugarAlmacenamientoNoExiste_DebeLanzarGeneralException() {
        // Arrange
        PlanEntregaMaritimaRequest planEntregaMaritimaRequest = new PlanEntregaMaritimaRequest();
        planEntregaMaritimaRequest.setClienteId(1);
        planEntregaMaritimaRequest.setPuertoEntrega(1);

        Cliente cliente = new Cliente();
        cliente.setId(1);

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));
        when(lugarAlmacenamientoRepository.findByIdAndClienteAndLogistica(anyInt(), eq(cliente), eq("MARITIMA")))
                .thenReturn(Optional.empty());

        // Act & Assert
        GeneralException exception = assertThrows(GeneralException.class,
                () -> planEntregaMaritimaService.guardarPlanEntregaMaritimo(planEntregaMaritimaRequest));

        assertEquals("tipo de producto null NO esta relacionado al cliente o   No es de tipo MARITIMA",
                exception.getMessage());
        verify(clienteRepository, times(1)).findById(1);
        verifyNoMoreInteractions(clienteRepository, lugarAlmacenamientoRepository);
    }
}