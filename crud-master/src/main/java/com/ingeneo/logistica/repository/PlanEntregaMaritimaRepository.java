package com.ingeneo.logistica.repository;

import com.ingeneo.logistica.entity.PlanEntregaMaritimaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanEntregaMaritimaRepository extends JpaRepository<PlanEntregaMaritimaEntity, String>{

    @Query("SELECT p FROM PlanEntregaMaritimaEntity p WHERE (:numeroGuia IS NULL OR p.numeroGuia = :numeroGuia) " +
            "AND (:cantidadProducto IS NULL OR p.cantidadProducto = :cantidadProducto) " +
            "AND (:fechaRegistro IS NULL OR p.fechaRegistro = :fechaRegistro) " +
            "AND (:fechaEntrega IS NULL OR p.fechaEntrega = :fechaEntrega) " +
            "AND (:precioEnvio IS NULL OR p.precioEnvio = :precioEnvio) " +
            "AND (:precioEnvioConDescuento IS NULL OR p.precioEnvioConDescuento = :precioEnvioConDescuento) " +
            "AND (:tipoProducto IS NULL OR p.tipoProducto = :tipoProducto) " +
            "AND (:lugarAlmacenamiento IS NULL OR p.puertoEntrega = :lugarAlmacenamiento) " +
            "AND (:numeroFlota IS NULL OR p.numeroFlota = :numeroFlota) " +
            "AND (:nombreCliente IS NULL OR p.nombreCliente = :nombreCliente) " +
            "AND (:lugarAlmacenamiento IS NULL OR p.puertoEntrega = :lugarAlmacenamiento) " +
            "AND (:idCliente IS NULL OR p.IdCliente = :idCliente)")
    List<PlanEntregaMaritimaEntity> search(@Param("numeroGuia") String numeroGuia,
                                           @Param("cantidadProducto") Integer cantidadProducto,
                                           @Param("fechaRegistro") String fechaRegistro,
                                           @Param("fechaEntrega") String fechaEntrega,
                                           @Param("precioEnvio") Double precioEnvio,
                                           @Param("precioEnvioConDescuento") Double precioEnvioConDescuento,
                                           @Param("numeroFlota") String numeroFlota,
                                           @Param("tipoProducto") String tipoProducto,
                                           @Param("lugarAlmacenamiento") String lugarAlmacenamiento,
                                           @Param("nombreCliente") String nombreCliente,
                                           @Param("idCliente") Integer idCliente);

}
