package com.ingeneo.logistica.repository;

import com.ingeneo.logistica.entity.PlanEntregaTerrestreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanEntregaTerrestreRepository extends JpaRepository<PlanEntregaTerrestreEntity, String> {

    @Query("SELECT p FROM PlanEntregaTerrestreEntity p WHERE (:numeroGuia IS NULL OR p.numeroGuia = :numeroGuia) " +
            "AND (:cantidadProducto IS NULL OR p.cantidadProducto = :cantidadProducto) " +
            "AND (:fechaRegistro IS NULL OR p.fechaRegistro = :fechaRegistro) " +
            "AND (:fechaEntrega IS NULL OR p.fechaEntrega = :fechaEntrega) " +
            "AND (:precioEnvio IS NULL OR p.precioEnvio = :precioEnvio) " +
            "AND (:precioEnvioConDescuento IS NULL OR p.precioEnvioConDescuento = :precioEnvioConDescuento) " +
            "AND (:tipoproducto IS NULL OR p.tipoproducto = :tipoproducto) " +
            "AND (:lugarAlmacenamiento IS NULL OR p.lugarAlmacenamiento = :lugarAlmacenamiento) " +
            "AND (:nombreCliente IS NULL OR p.nombreCliente = :nombreCliente) " +
            "AND (:idCliente IS NULL OR p.idDelCliente = :idCliente) " +
            "AND (:placaVehiculo IS NULL OR p.placaVehiculo = :placaVehiculo)")
    List<PlanEntregaTerrestreEntity> search(@Param("numeroGuia") String numeroGuia,
                                            @Param("cantidadProducto") Integer cantidadProducto,
                                            @Param("fechaRegistro") String fechaRegistro,
                                            @Param("fechaEntrega") String fechaEntrega,
                                            @Param("precioEnvio") Double precioEnvio,
                                            @Param("precioEnvioConDescuento") Double precioEnvioConDescuento,
                                            @Param("placaVehiculo") String placaVehiculo,
                                            @Param("tipoproducto") String tipoproducto,
                                            @Param("lugarAlmacenamiento") String lugarAlmacenamiento,
                                            @Param("nombreCliente")String nombreCliente,
                                            @Param("idCliente") Integer idCliente);
}
