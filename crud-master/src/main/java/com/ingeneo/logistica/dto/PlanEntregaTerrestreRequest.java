package com.ingeneo.logistica.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class PlanEntregaTerrestreRequest {

    @Min(value = 1, message = "La cantidad de producto debe ser mayor a 0")
    private Integer tipoProducto;

    @Min(value = 1, message = "La cantidad de producto debe ser mayor a 0")
    private Integer cantidadProducto;

    private String fechaRegistro;

    private String fechaEntrega;

    @Min(value = 1, message = "La cantidad de producto debe ser mayor a 0")
    private Integer bodegaEntrega;

    @Min(value = 1, message = "La cantidad de producto debe ser mayor a 0")
    private Integer clienteId;

    @Min(value = 1, message = "La cantidad de producto debe ser mayor a 0")
    private double precioEnvio;

    @Pattern(regexp = "[A-Z]{3}\\d{3}", message = "La placa del vehículo debe tener el formato AAA999")
    private String placaVehiculo;

}
