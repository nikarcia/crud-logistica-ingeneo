package com.ingeneo.logistica.controller;

import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.Cliente;
import com.ingeneo.logistica.service.ClienteService;
import com.ingeneo.logistica.service.impl.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

	@Autowired
	private  ClienteService clienteServiceImpl;


	@GetMapping
	public ResponseEntity<List<Cliente>> listarClientes() {
		List<Cliente> clientes = clienteServiceImpl.listarClientes();
		return ResponseEntity.ok(clientes);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<Cliente> guardarCliente(@Valid @RequestBody Cliente cliente) throws GeneralException {
		Cliente clienteGuardado = clienteServiceImpl.guardarCliente(cliente);
		return new ResponseEntity<>(clienteGuardado,HttpStatus.CREATED);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> actualizarCliente(@PathVariable Integer id, @Valid @RequestBody Cliente cliente) throws GeneralException {
		Cliente clienteActualizado = clienteServiceImpl.actualizarCliente(id, cliente);
		if (clienteActualizado == null) {
			return ResponseEntity.unprocessableEntity().build();
		}
		return ResponseEntity.noContent().build();
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) throws GeneralException {
		boolean eliminado = clienteServiceImpl.eliminarCliente(id);
		if (eliminado) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.unprocessableEntity().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Integer id) throws GeneralException {
		Cliente cliente = clienteServiceImpl.obtenerClientePorId(id);
		if (cliente == null) {
			return ResponseEntity.unprocessableEntity().build();
		}
		return ResponseEntity.ok(cliente);
	}
}
