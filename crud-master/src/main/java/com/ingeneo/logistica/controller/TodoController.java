package com.ingeneo.logistica.controller;

import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.Todo;
import com.ingeneo.logistica.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

	@Autowired
	private  TodoService clienteServiceImpl;


	@GetMapping
	public ResponseEntity<List<Todo>> listarTodos() {
		List<Todo> clientes = clienteServiceImpl.listarTodos();
		return ResponseEntity.ok(clientes);
	}

	@PostMapping
	public ResponseEntity<Todo> guardarTodo(@Valid @RequestBody Todo cliente) throws GeneralException {
		Todo clienteGuardado = clienteServiceImpl.guardarTodo(cliente);
		return new ResponseEntity<>(clienteGuardado,HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Todo> actualizarTodo(@PathVariable Integer id, @Valid @RequestBody Todo cliente) throws GeneralException {
		Todo clienteActualizado = clienteServiceImpl.actualizarTodo(id, cliente);
		if (clienteActualizado == null) {
			return ResponseEntity.unprocessableEntity().build();
		}
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarTodo(@PathVariable Integer id) throws GeneralException {
		boolean eliminado = clienteServiceImpl.eliminarTodo(id);
		if (eliminado) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.unprocessableEntity().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Todo> obtenerTodoPorId(@PathVariable Integer id) throws GeneralException {
		Todo cliente = clienteServiceImpl.obtenerTodoPorId(id);
		if (cliente == null) {
			return ResponseEntity.unprocessableEntity().build();
		}
		return ResponseEntity.ok(cliente);
	}
}
