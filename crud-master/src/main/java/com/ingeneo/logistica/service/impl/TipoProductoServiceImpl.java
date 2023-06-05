package com.ingeneo.logistica.service.impl;

import com.ingeneo.logistica.dto.TipoProductoDto;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.Cliente;
import com.ingeneo.logistica.entity.TipoProductoEntity;
import com.ingeneo.logistica.repository.ClienteRepository;
import com.ingeneo.logistica.repository.TipoProductoRepository;
import com.ingeneo.logistica.service.TipoProductoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class TipoProductoServiceImpl implements TipoProductoService {
@Autowired
    private  TipoProductoRepository tipoProductoRepository;
@Autowired
    private  ClienteRepository clienteRepository;

    public List<TipoProductoDto> listarTipoProductos() {
        List<TipoProductoEntity> tipoProductoEntities = tipoProductoRepository.findAll();
        List<TipoProductoDto> tipoProductoDtos = new ArrayList<>();

        for (TipoProductoEntity tipoProductoEntity : tipoProductoEntities) {
            TipoProductoDto tipoProductoDto = new TipoProductoDto();
            tipoProductoDto.setNombre(tipoProductoEntity.getNombre());
            tipoProductoDto.setId(tipoProductoEntity.getId());
            tipoProductoDto.setLogistica(tipoProductoEntity.getLogistica());
            tipoProductoDto.setClienteId(tipoProductoEntity.getCliente().getId());
            tipoProductoDtos.add(tipoProductoDto);
        }

        return tipoProductoDtos;
    }

    public List<TipoProductoEntity> getProductosByCliente(Integer cliente_id) throws GeneralException {
        Optional<Cliente> optionalCliente = clienteRepository.findById(cliente_id);
        if (!optionalCliente.isPresent()) {
            throw new GeneralException("cliente con id " + cliente_id + " no existe en db", HttpStatus.NOT_FOUND);
        }
        return tipoProductoRepository.findByCliente(optionalCliente.get());
    }

    public List<TipoProductoEntity> getProductosByClienteAndLogistica(Integer cliente_id, String logistica) throws GeneralException {
        Optional<Cliente> optionalCliente = clienteRepository.findById(cliente_id);
        if (!optionalCliente.isPresent()) {
            throw new GeneralException("cliente con id " + cliente_id + " no existe en db", HttpStatus.NOT_FOUND);
        }
        return tipoProductoRepository.findByClienteAndLogistica(optionalCliente.get(), logistica);
    }

    public TipoProductoEntity guardarTipoProducto(TipoProductoDto tipoProductoDto) throws GeneralException {
        if (tipoProductoRepository.existsByNombre(tipoProductoDto.getNombre())){
            throw new GeneralException("tipo de producto  " + tipoProductoDto.getNombre() + " ya existe en db", HttpStatus.BAD_REQUEST);
        }

        Optional<Cliente> optionalCliente = clienteRepository.findById(tipoProductoDto.getClienteId());
        if (!optionalCliente.isPresent()){
            throw new GeneralException("cliente  " + tipoProductoDto.getClienteId() + " No existe en db", HttpStatus.NOT_FOUND);
        }
        TipoProductoEntity tipoProductoEntity = new TipoProductoEntity();
        tipoProductoEntity.setLogistica(tipoProductoDto.getLogistica());
        tipoProductoEntity.setNombre(tipoProductoDto.getNombre());
        tipoProductoEntity.setCliente(optionalCliente.get());

        return tipoProductoRepository.save(tipoProductoEntity);
    }

    public TipoProductoEntity actualizarTipoProducto(Integer id, TipoProductoDto tipoProductoDto) throws GeneralException {

        Optional<TipoProductoEntity> tipoProductoEntityOptional = tipoProductoRepository.findById(id);
        if (!tipoProductoEntityOptional.isPresent()) {
            throw new GeneralException("tipo producto   " + id + " No existe en db", HttpStatus.NOT_FOUND);
        }

        if (tipoProductoRepository.existsByNombre(tipoProductoDto.getNombre())){
            throw new GeneralException("tipo de producto  " + tipoProductoDto.getNombre() + " ya existe en db", HttpStatus.BAD_REQUEST);
        }

        Optional<Cliente> optionalCliente = clienteRepository.findById(tipoProductoDto.getClienteId());
        if (!optionalCliente.isPresent()){
            throw new GeneralException("cliente  " + tipoProductoDto.getClienteId() + " No existe en db", HttpStatus.NOT_FOUND);
        }

        TipoProductoEntity tipoProductoEntity = new TipoProductoEntity();
        tipoProductoEntity.setId(id);
        tipoProductoEntity.setLogistica(tipoProductoDto.getLogistica());
        tipoProductoEntity.setNombre(tipoProductoDto.getNombre());
        tipoProductoEntity.setCliente(optionalCliente.get());

        return tipoProductoRepository.save(tipoProductoEntity);
    }

    public boolean eliminarTipoProducto(Integer id) throws GeneralException {
        Optional<TipoProductoEntity> tipoProductoEntityOptional = tipoProductoRepository.findById(id);
        if (!tipoProductoEntityOptional.isPresent()) {
            throw new GeneralException("tipo Producto  " + id + " No existe en db", HttpStatus.NOT_FOUND);
        }
        tipoProductoRepository.delete(tipoProductoEntityOptional.get());
        return true;
    }

    public TipoProductoEntity obtenerTipoProductoPorId(Integer id) {
        Optional<TipoProductoEntity> tipoProductoEntityOptional = tipoProductoRepository.findById(id);
        return tipoProductoEntityOptional.get();
    }
}
