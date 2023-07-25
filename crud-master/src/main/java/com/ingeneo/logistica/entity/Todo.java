package com.ingeneo.logistica.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "todo")
@Data
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	private String title;

	@NotNull
	private Boolean completed;


//	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
//	private Set<TipoProductoEntity> tipoproductoTerrestres = new HashSet<>();
//
//	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
//	private Set<LugarAlmacenamientoEntity> bodegaAlmacenamientoTerrestres = new HashSet<>();

}
