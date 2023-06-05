package com.ingeneo.logistica.controller;

import com.ingeneo.logistica.dto.TipoProductoDto;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.TipoProductoEntity;
import com.ingeneo.logistica.repository.ClienteRepository;
import com.ingeneo.logistica.service.TipoProductoService;
import com.ingeneo.logistica.service.impl.TipoProductoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tipo_producto")
public class TipoProductoController {

	private final TipoProductoService tipoProductoServiceImpl;
	@Autowired
	private  ClienteRepository clienteRepository;

	@Autowired
	public TipoProductoController(TipoProductoServiceImpl tipoProductoServiceImpl, ClienteRepository clienteRepository) {
		this.tipoProductoServiceImpl = tipoProductoServiceImpl;
	}

	@GetMapping
	public ResponseEntity<List<TipoProductoDto>> listarTipoProductoEntitys() {
		List<TipoProductoDto> tipoProductoDtos = tipoProductoServiceImpl.listarTipoProductos();
		return ResponseEntity.ok(tipoProductoDtos);
	}

	@GetMapping("/cliente/{cliente_id}")
	public ResponseEntity<List<TipoProductoEntity>> getProductosByCliente(@PathVariable Integer cliente_id) throws GeneralException {
		List<TipoProductoEntity> tipoProductoEntities = tipoProductoServiceImpl.getProductosByCliente(cliente_id);
		return ResponseEntity.ok(tipoProductoEntities);
	}

	@GetMapping("/cliente/{cliente_id}/logistica/{logistica}")
	public ResponseEntity<List<TipoProductoEntity>> getProductosByClienteAndLogistica(@PathVariable Integer cliente_id,
																					  @PathVariable String logistica) throws GeneralException {
		List<TipoProductoEntity> tipoProductoEntities = tipoProductoServiceImpl.getProductosByClienteAndLogistica(
				cliente_id, logistica);
		return ResponseEntity.ok(tipoProductoEntities);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<TipoProductoEntity> guardarTipoProductoEntity(@Valid @RequestBody TipoProductoDto tipoProductoDto) throws GeneralException {
		TipoProductoEntity tipoProductoEntityGuardado = tipoProductoServiceImpl.guardarTipoProducto(tipoProductoDto);

		return new ResponseEntity<>(tipoProductoEntityGuardado, HttpStatus.CREATED);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<TipoProductoEntity> actualizarTipoProductoEntity(@Valid @RequestBody TipoProductoDto tipoProductoDto,
																		   @PathVariable Integer id) throws GeneralException {
		TipoProductoEntity tipoProductoEntityActualizado = tipoProductoServiceImpl.actualizarTipoProducto(id, tipoProductoDto);
		if (tipoProductoEntityActualizado == null) {
			return ResponseEntity.unprocessableEntity().build();
		}
		return ResponseEntity.noContent().build();
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarTipoProductoEntity(@PathVariable Integer id) throws GeneralException {
		boolean eliminado = tipoProductoServiceImpl.eliminarTipoProducto(id);
		if (eliminado) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.unprocessableEntity().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<TipoProductoEntity> obtenerTipoProductoEntityPorId(@PathVariable Integer id) {
		TipoProductoEntity tipoProductoEntity = tipoProductoServiceImpl.obtenerTipoProductoPorId(id);
		if (tipoProductoEntity == null) {
			return ResponseEntity.unprocessableEntity().build();
		}
		return ResponseEntity.ok(tipoProductoEntity);
	}
}
