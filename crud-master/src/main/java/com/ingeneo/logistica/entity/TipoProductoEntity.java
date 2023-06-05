package com.ingeneo.logistica.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ingeneo.logistica.entity.Cliente;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tipo_producto",uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
@Data
public class TipoProductoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	private String nombre;

	@NotEmpty
	private String logistica;


	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cliente_id")
	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Cliente cliente;


}
