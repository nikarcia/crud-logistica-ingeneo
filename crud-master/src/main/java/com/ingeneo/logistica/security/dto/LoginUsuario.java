package com.ingeneo.logistica.security.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUsuario {
    @NotBlank
    private String nombreUsuario;
    @NotBlank
    private String password;

}
