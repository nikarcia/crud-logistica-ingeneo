package com.ingeneo.logistica.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cliente",uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
@Data
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	private String nombre;

//	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
//	private Set<TipoProductoEntity> tipoproductoTerrestres = new HashSet<>();
//
//	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
//	private Set<LugarAlmacenamientoEntity> bodegaAlmacenamientoTerrestres = new HashSet<>();

	public Cliente(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}


	public Cliente() {
	}
}
