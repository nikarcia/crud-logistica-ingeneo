package com.ingeneo.logistica.dto;

import com.ingeneo.logistica.entity.Cliente;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class TipoProductoDto {
	private Integer id;
	@NotEmpty
	private String nombre;

	@NotEmpty(message = "La logística no puede ser nula")
	@Pattern(regexp = "MARITIMA|TERRESTRE", message = "La logística debe ser de tipo Maritima 0 terrestre")
	private String logistica;
	@NotNull(message = "El cliente ID no puede ser nulo")
	@Min(value = 1, message = "El cliente ID debe ser mayor a 0")
	private Integer clienteId;
	
}
