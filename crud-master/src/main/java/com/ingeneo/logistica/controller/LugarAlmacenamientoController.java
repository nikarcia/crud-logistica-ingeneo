package com.ingeneo.logistica.controller;

import com.ingeneo.logistica.dto.LugarAlmacenamientoDto;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.Cliente;
import com.ingeneo.logistica.entity.LugarAlmacenamientoEntity;
import com.ingeneo.logistica.service.LugarAlmacenamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lugar_almacenamiento")
public class LugarAlmacenamientoController {

	@Autowired
	private LugarAlmacenamientoService lugarAlmacenamientoService;

	@GetMapping
	public ResponseEntity<List<LugarAlmacenamientoDto>> listarLugaresAlmacenamiento() {
		List<LugarAlmacenamientoDto> lugaresAlmacenamientoDto = lugarAlmacenamientoService.listarLugaresAlmacenamiento();
		return ResponseEntity.ok(lugaresAlmacenamientoDto);
	}

	@GetMapping("/cliente/{cliente_id}")
	public ResponseEntity<List<LugarAlmacenamientoEntity>> getLugaresAlmacenamientoByCliente(@PathVariable Integer cliente_id) throws GeneralException {
		List<LugarAlmacenamientoEntity> lugarAlmacenamientoEntities = lugarAlmacenamientoService.getLugaresAlmacenamientoByCliente(cliente_id);
		return ResponseEntity.ok(lugarAlmacenamientoEntities);
	}

	@GetMapping("/cliente/{cliente_id}/logistica/{logistica}")
	public ResponseEntity<List<LugarAlmacenamientoDto>> getLugaresAlmacenamientoByClienteAndLogistica(@PathVariable Integer cliente_id, @PathVariable String logistica) throws GeneralException {
		List<LugarAlmacenamientoDto> lugaresAlmacenamientoDto = lugarAlmacenamientoService.getLugaresAlmacenamientoByClienteAndLogistica(cliente_id, logistica);
		return ResponseEntity.ok(lugaresAlmacenamientoDto);
	}

	@PostMapping
	public ResponseEntity<LugarAlmacenamientoDto> guardarLugarAlmacenamiento(@Valid @RequestBody LugarAlmacenamientoDto lugarAlmacenamientoDto) throws GeneralException {
		LugarAlmacenamientoDto lugarAlmacenamientoGuardado = lugarAlmacenamientoService.guardarLugarAlmacenamiento(lugarAlmacenamientoDto);
		return new ResponseEntity<>(lugarAlmacenamientoGuardado, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<LugarAlmacenamientoDto> actualizarLugarAlmacenamiento(@Valid @RequestBody LugarAlmacenamientoDto lugarAlmacenamientoDto, @PathVariable Integer id) throws GeneralException {
		LugarAlmacenamientoDto lugarAlmacenamientoActualizado = lugarAlmacenamientoService.actualizarLugarAlmacenamiento(lugarAlmacenamientoDto, id);
		return ResponseEntity.ok(lugarAlmacenamientoActualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarLugarAlmacenamiento(@PathVariable Integer id) throws GeneralException {
		lugarAlmacenamientoService.eliminarLugarAlmacenamiento(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<LugarAlmacenamientoDto> obtenerLugarAlmacenamientoPorId(@PathVariable Integer id) throws GeneralException {
		LugarAlmacenamientoDto lugarAlmacenamientoDto = lugarAlmacenamientoService.obtenerLugarAlmacenamientoPorId(id);
		if (lugarAlmacenamientoDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lugarAlmacenamientoDto);
	}
}
