package com.ingeneo.logistica.security.repository;

import com.ingeneo.logistica.security.entity.Rol;
import com.ingeneo.logistica.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
