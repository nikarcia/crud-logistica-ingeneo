package com.ingeneo.logistica.service;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> listarTodos();

    Todo guardarTodo(Todo todo) throws GeneralException;

    Todo actualizarTodo(Integer id, Todo todo) throws GeneralException;

    boolean eliminarTodo(Integer id) throws GeneralException;

    Todo obtenerTodoPorId(Integer id) throws GeneralException;
}
