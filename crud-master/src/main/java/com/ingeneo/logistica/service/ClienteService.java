package com.ingeneo.logistica.service;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> listarClientes();

    Cliente guardarCliente(Cliente cliente) throws GeneralException;

    Cliente actualizarCliente(Integer id, Cliente cliente) throws GeneralException;

    boolean eliminarCliente(Integer id) throws GeneralException;

    Cliente obtenerClientePorId(Integer id) throws GeneralException;
}
