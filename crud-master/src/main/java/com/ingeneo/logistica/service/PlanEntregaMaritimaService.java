package com.ingeneo.logistica.service;

import com.ingeneo.logistica.dto.PlanEntregaMaritimaRequest;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.PlanEntregaMaritimaEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PlanEntregaMaritimaService {
    String generateUniqueNumber();

    List<PlanEntregaMaritimaEntity> searchPlanesEntregaMaritimo(String numeroGuia, Integer cantidadProducto, String fechaRegistro,
                                                                String fechaEntrega, Double precioEnvio,Double precioEnvioConDescuento, String placaVehiculo , String tipoProducto , String lugarAlmacenamiento, String nombreCliente, Integer idCliente);

    ResponseEntity<PlanEntregaMaritimaEntity> guardarPlanEntregaMaritimo(PlanEntregaMaritimaRequest planEntregaMaritimaRequest) throws GeneralException;

    ResponseEntity<PlanEntregaMaritimaEntity> actualizarPlanEntregaMaritimo(PlanEntregaMaritimaRequest planEntregaMaritimaRequest, String numeroGuia) throws GeneralException;

    ResponseEntity<PlanEntregaMaritimaEntity> eliminarPlanEntregaMaritimo(String numeroGuia) throws GeneralException;

    ResponseEntity<PlanEntregaMaritimaEntity> obtenerPlanEntregaMaritimoPorId(String numeroGuia) throws GeneralException;
}
