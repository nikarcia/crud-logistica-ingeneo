package com.ingeneo.logistica.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "lugar_almacenamiento",uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LugarAlmacenamientoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String nombre;

	@NotNull
	private String direccion;

	@NotNull
	private String logistica;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cliente_id")
	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Cliente cliente;

}
