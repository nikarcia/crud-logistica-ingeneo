package com.ingeneo.logistica.service;

import com.ingeneo.logistica.dto.LugarAlmacenamientoDto;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.LugarAlmacenamientoEntity;

import java.util.List;

public interface LugarAlmacenamientoService {
    List<LugarAlmacenamientoDto> listarLugaresAlmacenamiento();
    List<LugarAlmacenamientoEntity> getLugaresAlmacenamientoByCliente(Integer cliente_id) throws GeneralException;
    List<LugarAlmacenamientoDto> getLugaresAlmacenamientoByClienteAndLogistica(Integer cliente_id, String logistica) throws GeneralException;
    LugarAlmacenamientoDto guardarLugarAlmacenamiento(LugarAlmacenamientoDto lugarAlmacenamientoDto) throws GeneralException;
    LugarAlmacenamientoDto actualizarLugarAlmacenamiento(LugarAlmacenamientoDto lugarAlmacenamientoDto, Integer id) throws GeneralException;
    void eliminarLugarAlmacenamiento(Integer id) throws GeneralException;
    LugarAlmacenamientoDto obtenerLugarAlmacenamientoPorId(Integer id) throws GeneralException;
}
