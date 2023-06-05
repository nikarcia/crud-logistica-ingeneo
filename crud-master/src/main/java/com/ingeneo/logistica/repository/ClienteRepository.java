package com.ingeneo.logistica.repository;

import com.ingeneo.logistica.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
    boolean existsByNombre(String nombre);


}
