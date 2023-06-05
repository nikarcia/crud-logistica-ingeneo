package com.ingeneo.logistica.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class LugarAlmacenamientoDto {

    private Integer id;

    @NotNull(message = "El cliente ID no puede ser nulo")
    @Min(value = 1, message = "El cliente ID debe ser mayor a 0")
    private Integer clienteId;

    @NotEmpty(message = "La logística no puede ser nula")
    @Pattern(regexp = "MARITIMA|TERRESTRE", message = "La logística debe ser de tipo Maritima 0 terrestre")
    private String logistica;

    @NotEmpty(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotEmpty(message = "direccion no puede estar vacío")
    private String direccion;
}
