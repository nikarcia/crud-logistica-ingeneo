package com.ingeneo.logistica.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "PlanEntregaTerrestre")
@Data
public class PlanEntregaTerrestreEntity {

		@Id
		@Pattern(regexp = "[A-Za-z0-9]{10}")
		private String numeroGuia;

		private Integer cantidadProducto;

		private String fechaRegistro;

		private String fechaEntrega;

		private Double precioEnvio;

	    private Double precioEnvioConDescuento;

		@Pattern(regexp = "[A-Z]{3}\\d{3}")
		private String placaVehiculo;

		private String tipoproducto;
		private String lugarAlmacenamiento;

	    @NotNull
	   private Integer idDelCliente ;

	   @NotEmpty
	   private String nombreCliente ;



//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "tipo_producto_id")
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//	private TipoProductoEntity tipoproducto;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cliente_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Cliente cliente;

//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "lugar_almacenamiento_id")
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//	private LugarAlmacenamientoEntity lugarAlmacenamiento;

}
