package com.ingeneo.logistica.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "plan_entrega_maritima")
@Data
public class PlanEntregaMaritimaEntity {
	@Id
	@NotNull
	@Pattern(regexp = "[A-Za-z0-9]{10}")
	private String numeroGuia;
	@NotEmpty
	private String tipoProducto;

	@NotNull
	@Min(value = 1, message = "La cantidad de producto debe ser mayor a 0")
	private Integer cantidadProducto;

	@NotEmpty
	private String fechaRegistro;
	@NotEmpty

	private String fechaEntrega;
	@NotEmpty
	private String puertoEntrega;

	@NotNull
	@Min(value = 1, message = "precioEnvio debe ser mayor a 0")
	private double precioEnvio;

	@NotNull
	@Min(value = 1, message = "precioEnvio debe ser mayor a 0")
	private double precioEnvioConDescuento;
	@Pattern(regexp = "[A-Z]{3}\\d{4}[A-Z]", message = "El número de flota debe tener el formato 'XXX0000X'")
	private String numeroFlota;


//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "tipo_producto_id")
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//	private TipoProductoEntity tipoproducto;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cliente_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Cliente cliente;

	@NotNull
	private Integer IdCliente ;

	@NotEmpty
	private String nombreCliente ;


//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "lugar_almacenamiento_id")
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//	private LugarAlmacenamientoEntity lugarAlmacenamiento;

}
