package com.ingeneo.logistica.service.impl;

import com.ingeneo.logistica.dto.LugarAlmacenamientoDto;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.Cliente;
import com.ingeneo.logistica.entity.LugarAlmacenamientoEntity;
import com.ingeneo.logistica.repository.ClienteRepository;
import com.ingeneo.logistica.repository.LugarAlmacenamientoRepository;
import com.ingeneo.logistica.service.LugarAlmacenamientoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class LugarAlmacenamientoServiceImpl implements LugarAlmacenamientoService {

    @Autowired
    private LugarAlmacenamientoRepository lugarAlmacenamientoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<LugarAlmacenamientoDto> listarLugaresAlmacenamiento() {
        List<LugarAlmacenamientoEntity> lugaresAlmacenamientoEntity = lugarAlmacenamientoRepository.findAll();
        List<LugarAlmacenamientoDto> lugaresAlmacenamientoDto = new ArrayList<>();
        for (LugarAlmacenamientoEntity lugarAlmacenamientoEntity : lugaresAlmacenamientoEntity) {
            lugaresAlmacenamientoDto.add(mapToDto(lugarAlmacenamientoEntity));
        }
        return lugaresAlmacenamientoDto;
    }

    @Override
    public List<LugarAlmacenamientoEntity> getLugaresAlmacenamientoByCliente(Integer cliente_id) throws GeneralException {
        Optional<Cliente> optionalCliente = clienteRepository.findById(cliente_id);
        if (!optionalCliente.isPresent()) {
            throw new GeneralException("cliente  " + cliente_id + " No existe en db", HttpStatus.NOT_FOUND);
        }
        Cliente cliente = optionalCliente.get();
        List<LugarAlmacenamientoEntity> lugaresAlmacenamientoEntity = lugarAlmacenamientoRepository.findByCliente(cliente);
        return lugaresAlmacenamientoEntity;
    }

    @Override
    public List<LugarAlmacenamientoDto> getLugaresAlmacenamientoByClienteAndLogistica(Integer cliente_id, String logistica) throws GeneralException {
        Optional<Cliente> optionalCliente = clienteRepository.findById(cliente_id);
        if (!optionalCliente.isPresent()) {
            throw new GeneralException("cliente  " + cliente_id + " No existe en db", HttpStatus.NOT_FOUND);
        }
        Cliente cliente = optionalCliente.get();
        List<LugarAlmacenamientoEntity> lugaresAlmacenamientoEntity = lugarAlmacenamientoRepository.findByClienteAndLogistica(cliente, logistica);
        List<LugarAlmacenamientoDto> lugaresAlmacenamientoDto = new ArrayList<>();
        for (LugarAlmacenamientoEntity lugarAlmacenamientoEntity : lugaresAlmacenamientoEntity) {
            lugaresAlmacenamientoDto.add(mapToDto(lugarAlmacenamientoEntity));
        }
        return lugaresAlmacenamientoDto;
    }

    @Override
    public LugarAlmacenamientoDto guardarLugarAlmacenamiento(LugarAlmacenamientoDto lugarAlmacenamientoDto) throws GeneralException {
        if (lugarAlmacenamientoRepository.existsByNombre(lugarAlmacenamientoDto.getNombre())){
            throw new GeneralException("lugar de almacenamiento nombre:" + lugarAlmacenamientoDto.getNombre() + " ya existe en db", HttpStatus.BAD_REQUEST);
        }

        Optional<Cliente> optionalCliente = clienteRepository.findById(lugarAlmacenamientoDto.getClienteId());
        if (!optionalCliente.isPresent()){
            throw new GeneralException("cliente  " + lugarAlmacenamientoDto.getClienteId() + " No existe en db", HttpStatus.NOT_FOUND);
        }
        Cliente cliente = optionalCliente.get();
        LugarAlmacenamientoEntity lugarAlmacenamientoEntity = mapToEntity(lugarAlmacenamientoDto);
        lugarAlmacenamientoEntity.setCliente(cliente);
        LugarAlmacenamientoEntity lugarAlmacenamientoGuardado = lugarAlmacenamientoRepository.save(lugarAlmacenamientoEntity);
        return mapToDto(lugarAlmacenamientoGuardado);
    }

    @Override
    public LugarAlmacenamientoDto actualizarLugarAlmacenamiento(LugarAlmacenamientoDto lugarAlmacenamientoDto, Integer id) throws GeneralException {
        Optional<LugarAlmacenamientoEntity> optionalLugarAlmacenamiento = lugarAlmacenamientoRepository.findById(id);
        if (!optionalLugarAlmacenamiento.isPresent()) {
            throw new GeneralException("lugar de almacenamiento   " + id + " No existe en db", HttpStatus.NOT_FOUND);
        }

        Optional<Cliente> optionalCliente = clienteRepository.findById(lugarAlmacenamientoDto.getClienteId());
        if (!optionalCliente.isPresent()) {
            throw new GeneralException("cliente  " + lugarAlmacenamientoDto.getClienteId() + " No existe en db", HttpStatus.NOT_FOUND);
        }

        Cliente cliente = optionalCliente.get();
        LugarAlmacenamientoEntity lugarAlmacenamientoEntity = new LugarAlmacenamientoEntity();
        lugarAlmacenamientoEntity.setId(id);
        lugarAlmacenamientoEntity.setCliente(cliente);
        lugarAlmacenamientoEntity.setLogistica(lugarAlmacenamientoDto.getLogistica());
        lugarAlmacenamientoEntity.setNombre(lugarAlmacenamientoDto.getNombre());
        LugarAlmacenamientoEntity lugarAlmacenamientoActualizado = lugarAlmacenamientoRepository.save(lugarAlmacenamientoEntity);
        return mapToDto(lugarAlmacenamientoActualizado);
    }

    @Override
    public void eliminarLugarAlmacenamiento(Integer id) throws GeneralException {
        Optional<LugarAlmacenamientoEntity> lugarAlmacenamientoEntityOptional = lugarAlmacenamientoRepository.findById(id);
        if (!lugarAlmacenamientoEntityOptional.isPresent()) {
            throw new GeneralException("lugar de almacenamiento   " + id + " No existe en db", HttpStatus.NOT_FOUND);
        }
        lugarAlmacenamientoRepository.deleteById(id);
    }

    @Override
    public LugarAlmacenamientoDto obtenerLugarAlmacenamientoPorId(Integer id) throws GeneralException {
        Optional<LugarAlmacenamientoEntity> lugarAlmacenamientoEntityOptional = lugarAlmacenamientoRepository.findById(id);
        if (!lugarAlmacenamientoEntityOptional.isPresent()) {
            throw new GeneralException("lugar de almacenamiento   " + id + " No existe en db", HttpStatus.NOT_FOUND);
        }
        return mapToDto(lugarAlmacenamientoEntityOptional.get());
    }

    private LugarAlmacenamientoDto mapToDto(LugarAlmacenamientoEntity lugarAlmacenamientoEntity) {
        LugarAlmacenamientoDto lugarAlmacenamientoDto = new LugarAlmacenamientoDto();
        lugarAlmacenamientoDto.setId(lugarAlmacenamientoEntity.getId());
        lugarAlmacenamientoDto.setClienteId(lugarAlmacenamientoEntity.getCliente().getId());
        lugarAlmacenamientoDto.setLogistica(lugarAlmacenamientoEntity.getLogistica());
        lugarAlmacenamientoDto.setNombre(lugarAlmacenamientoEntity.getNombre());
        lugarAlmacenamientoDto.setDireccion(lugarAlmacenamientoEntity.getDireccion());
        return lugarAlmacenamientoDto;
    }

    private LugarAlmacenamientoEntity mapToEntity(LugarAlmacenamientoDto lugarAlmacenamientoDto) {
        LugarAlmacenamientoEntity lugarAlmacenamientoEntity = new LugarAlmacenamientoEntity();
        lugarAlmacenamientoEntity.setLogistica(lugarAlmacenamientoDto.getLogistica());
        lugarAlmacenamientoEntity.setNombre(lugarAlmacenamientoDto.getNombre());
        lugarAlmacenamientoEntity.setDireccion(lugarAlmacenamientoDto.getDireccion());
        return lugarAlmacenamientoEntity;
    }
}
