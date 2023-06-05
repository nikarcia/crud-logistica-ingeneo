package com.ingeneo.logistica.repository;

import com.ingeneo.logistica.entity.Cliente;
import com.ingeneo.logistica.entity.TipoProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoProductoRepository extends JpaRepository<TipoProductoEntity, Integer>{

    List<TipoProductoEntity> findByCliente(Cliente cliente);

    public List<TipoProductoEntity> findByClienteAndLogistica(Cliente cliente, String logistica);

    Optional<TipoProductoEntity> findByIdAndClienteAndLogistica(Integer id, Cliente cliente, String logistica);


    boolean existsByNombre(String nombre);
}
