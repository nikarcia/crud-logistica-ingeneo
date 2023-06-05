package com.ingeneo.logistica.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class PlanEntregaMaritimaRequest {

    @NotNull
    @Min(value = 1, message = "tipoProducto debe ser mayor a 0")
    private Integer tipoProducto;

    @NotNull
    @Min(value = 1, message = "La cantidad de producto debe ser mayor a 0")
    private Integer cantidadProducto;

    @NotEmpty
    private String fechaRegistro;
    @NotEmpty

    private String fechaEntrega;
    @NotNull
    @Min(value = 1, message = "La cantidad de producto debe ser mayor a 0")
    private Integer puertoEntrega;
    @NotNull
    @Min(value = 1, message = "clienteId debe ser mayor a 0")
    private Integer clienteId;
    @NotNull
    @Min(value = 1, message = "precioEnvio debe ser mayor a 0")
    private double precioEnvio;
    @Pattern(regexp = "[A-Z]{3}\\d{4}[A-Z]", message = "El número de flota debe tener el formato 'XXX0000X'")
    private String numeroFlota;
}
