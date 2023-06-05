package com.ingeneo.logistica.util;

import com.ingeneo.logistica.security.entity.Rol;
import com.ingeneo.logistica.security.entity.Usuario;
import com.ingeneo.logistica.security.enums.RolNombre;
import com.ingeneo.logistica.security.service.RolService;
import com.ingeneo.logistica.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * MUY IMPORTANTE: ESTA CLASE SÓLO SE EJECUTARÁ UNA VEZ PARA CREAR LOS ROLES.
 * UNA VEZ CREADOS SE DEBERÁ ELIMINAR O BIEN COMENTAR EL CÓDIGO
 *
 */

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;

    @Autowired
    UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {

        if (!rolService.getByRolNombre(RolNombre.ROLE_ADMIN).isPresent()){
            Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
            rolService.save(rolAdmin);
        }
        if (!rolService.getByRolNombre(RolNombre.ROLE_USER).isPresent()){
            Rol rolUser = new Rol(RolNombre.ROLE_USER);
            rolService.save(rolUser);
        }

    }
}
