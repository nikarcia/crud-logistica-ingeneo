package com.ingeneo.logistica.service.impl;

import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.Cliente;
import com.ingeneo.logistica.repository.ClienteRepository;
import com.ingeneo.logistica.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente guardarCliente(Cliente cliente) throws GeneralException {
        if (clienteRepository.existsByNombre(cliente.getNombre())){
            throw new GeneralException("cliente " + cliente.getNombre() + " ya existe en db",HttpStatus.CONFLICT);
        }
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Integer id, Cliente cliente) throws GeneralException {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (!clienteOptional.isPresent()) {
            throw new GeneralException("cliente " + id + " No encontradob",HttpStatus.NOT_FOUND);
        }
        cliente.setId(id);
        return clienteRepository.save(cliente);    }

    public boolean eliminarCliente(Integer id) throws GeneralException {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (!clienteOptional.isPresent()) {
            throw new GeneralException("cliente " + id + " No encontrado ",HttpStatus.NOT_FOUND);

        }
        clienteRepository.delete(clienteOptional.get());
        return true;
    }

    public Cliente obtenerClientePorId(Integer id) throws GeneralException {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (!clienteOptional.isPresent()) {
            throw new GeneralException("cliente " + id + " No encontrado ",HttpStatus.NOT_FOUND);

        }
        return clienteOptional.get();
    }
}
