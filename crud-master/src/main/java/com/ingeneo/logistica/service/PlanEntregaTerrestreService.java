package com.ingeneo.logistica.service;

import com.ingeneo.logistica.dto.PlanEntregaTerrestreRequest;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.PlanEntregaTerrestreEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PlanEntregaTerrestreService {

    List<PlanEntregaTerrestreEntity> searchPlanesEntregaTerrestre(String numeroGuia, Integer cantidadProducto, String fechaRegistro,
                                                                  String fechaEntrega, Double precioEnvio, Double precioEnvioConDescuento, String placaVehiculo , String tipoproducto , String lugarAlmacenamiento, String nombreCliente, Integer idCliente);

    ResponseEntity<PlanEntregaTerrestreEntity> guardarPlanEntregaTerrestre(PlanEntregaTerrestreRequest planEntregaTerrestreRequest) throws GeneralException;

    ResponseEntity<PlanEntregaTerrestreEntity> actualizarPlanEntregaTerrestre(PlanEntregaTerrestreRequest planEntregaTerrestre, @PathVariable String numeroGuia) throws GeneralException;

    ResponseEntity<PlanEntregaTerrestreEntity> eliminarPlanEntregaTerrestre(String numeroGuia) throws GeneralException;

    ResponseEntity<PlanEntregaTerrestreEntity> obtenerPlanEntregaTerrestrePorId(String numeroGuia) throws GeneralException;

}
