package com.ingeneo.logistica.service;

import com.ingeneo.logistica.dto.TipoProductoDto;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.TipoProductoEntity;

import java.util.List;

public interface TipoProductoService {
    List<TipoProductoDto> listarTipoProductos();

    List<TipoProductoEntity> getProductosByCliente(Integer cliente_id) throws GeneralException;

    List<TipoProductoEntity> getProductosByClienteAndLogistica(Integer cliente_id, String logistica) throws GeneralException;

    TipoProductoEntity guardarTipoProducto(TipoProductoDto tipoProductoDto) throws GeneralException;

    TipoProductoEntity actualizarTipoProducto(Integer id, TipoProductoDto tipoProductoDto) throws GeneralException;

    boolean eliminarTipoProducto(Integer id) throws GeneralException;

    TipoProductoEntity obtenerTipoProductoPorId(Integer id);
}
