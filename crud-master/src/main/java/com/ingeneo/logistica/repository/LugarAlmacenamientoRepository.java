package com.ingeneo.logistica.repository;

import com.ingeneo.logistica.entity.Cliente;
import com.ingeneo.logistica.entity.LugarAlmacenamientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LugarAlmacenamientoRepository extends JpaRepository<LugarAlmacenamientoEntity, Integer>{

    List<LugarAlmacenamientoEntity> findByCliente(Cliente cliente);

    public List<LugarAlmacenamientoEntity> findByClienteAndLogistica(Cliente cliente, String logistica);

    Optional<LugarAlmacenamientoEntity> findByIdAndClienteAndLogistica(Integer puertoEntrega,Cliente cliente, String logistica);

    boolean existsByNombre(String nombre);
}
