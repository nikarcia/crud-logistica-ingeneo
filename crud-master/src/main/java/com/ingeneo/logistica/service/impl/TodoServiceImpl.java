package com.ingeneo.logistica.service.impl;

import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.Todo;
import com.ingeneo.logistica.repository.TodoRepository;
import com.ingeneo.logistica.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository clienteRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Todo> listarTodos() {
        return clienteRepository.findAll();
    }

    public Todo guardarTodo(Todo cliente) throws GeneralException {
        return clienteRepository.save(cliente);
    }

    public Todo actualizarTodo(Integer id, Todo cliente) throws GeneralException {
        Optional<Todo> clienteOptional = clienteRepository.findById(id);
        if (!clienteOptional.isPresent()) {
            throw new GeneralException("cliente " + id + " No encontradob",HttpStatus.NOT_FOUND);
        }
        cliente.setId(id);
        return clienteRepository.save(cliente);    }

    public boolean eliminarTodo(Integer id) throws GeneralException {
        Optional<Todo> clienteOptional = clienteRepository.findById(id);
        if (!clienteOptional.isPresent()) {
            throw new GeneralException("cliente " + id + " No encontrado ",HttpStatus.NOT_FOUND);

        }
        clienteRepository.delete(clienteOptional.get());
        return true;
    }

    public Todo obtenerTodoPorId(Integer id) throws GeneralException {
        Optional<Todo> clienteOptional = clienteRepository.findById(id);
        if (!clienteOptional.isPresent()) {
            throw new GeneralException("cliente " + id + " No encontrado ",HttpStatus.NOT_FOUND);

        }
        return clienteOptional.get();
    }
}
