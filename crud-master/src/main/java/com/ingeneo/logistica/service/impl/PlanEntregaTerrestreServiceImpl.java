package com.ingeneo.logistica.service.impl;

import com.ingeneo.logistica.dto.PlanEntregaTerrestreRequest;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.*;
import com.ingeneo.logistica.repository.ClienteRepository;
import com.ingeneo.logistica.repository.LugarAlmacenamientoRepository;
import com.ingeneo.logistica.repository.PlanEntregaTerrestreRepository;
import com.ingeneo.logistica.repository.TipoProductoRepository;
import com.ingeneo.logistica.service.PlanEntregaTerrestreService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanEntregaTerrestreServiceImpl implements PlanEntregaTerrestreService {

    @Autowired
    private PlanEntregaTerrestreRepository planEntregaTerrestreRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Autowired
    private LugarAlmacenamientoRepository lugarAlmacenamientoRepository;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 10;

    private final Random random = new SecureRandom();

    public String generateUniqueNumber() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
    public List<PlanEntregaTerrestreEntity> searchPlanesEntregaTerrestre(String numeroGuia, Integer cantidadProducto, String fechaRegistro,
                                                                         String fechaEntrega, Double precioEnvio, Double precioEnvioConDescuento, String placaVehiculo , String tipoproducto , String lugarAlmacenamiento, String nombreCliente, Integer idCliente) {
        // Lógica para filtrar y buscar planes de entrega terrestre en función de los parámetros recibidos
        return planEntregaTerrestreRepository.search(numeroGuia, cantidadProducto, fechaRegistro, fechaEntrega, precioEnvio,precioEnvioConDescuento, placaVehiculo ,tipoproducto , lugarAlmacenamiento , nombreCliente ,idCliente );
    }

    public ResponseEntity<PlanEntregaTerrestreEntity> guardarPlanEntregaTerrestre(PlanEntregaTerrestreRequest planEntregaTerrestreRequest) throws GeneralException {
        Optional<Cliente> optionalCliente = clienteRepository.findById(planEntregaTerrestreRequest.getClienteId());

        if(!optionalCliente.isPresent()){
            throw new GeneralException("cliente   " + planEntregaTerrestreRequest.getClienteId() + " No existe en db", HttpStatus.NOT_FOUND);
        }
        Optional<TipoProductoEntity> tipoProductoEntityOptional = tipoProductoRepository.findByIdAndClienteAndLogistica(planEntregaTerrestreRequest.getTipoProducto(),optionalCliente.get(),"TERRESTRE");
        if(!tipoProductoEntityOptional.isPresent()){
            throw new GeneralException("tipo de producto " + planEntregaTerrestreRequest.getTipoProducto() + "  No esta relacionado al cliente o   No es de tipo TERRESTRE", HttpStatus.BAD_REQUEST);
        }
        Optional<LugarAlmacenamientoEntity> lugarAlmacenamientoEntityOptional = lugarAlmacenamientoRepository.findByIdAndClienteAndLogistica(planEntregaTerrestreRequest.getBodegaEntrega(),optionalCliente.get(),"TERRESTRE");
        if(!lugarAlmacenamientoEntityOptional.isPresent()){
            throw new GeneralException("bodega de entrega   " + planEntregaTerrestreRequest.getBodegaEntrega() + "  No esta relacionado al cliente o   No es de tipo TERRESTRE", HttpStatus.BAD_REQUEST);
        }

        PlanEntregaTerrestreEntity planEntregaTerrestreEntity = new PlanEntregaTerrestreEntity();
        planEntregaTerrestreEntity.setCliente(optionalCliente.get());
        planEntregaTerrestreEntity.setIdDelCliente(optionalCliente.get().getId());
        planEntregaTerrestreEntity.setNombreCliente(optionalCliente.get().getNombre());
        planEntregaTerrestreEntity.setTipoproducto(tipoProductoEntityOptional.get().getNombre().toString());
        planEntregaTerrestreEntity.setLugarAlmacenamiento(lugarAlmacenamientoEntityOptional.get().getNombre().toString());
        planEntregaTerrestreEntity.setFechaEntrega(planEntregaTerrestreRequest.getFechaEntrega());
        planEntregaTerrestreEntity.setCantidadProducto(planEntregaTerrestreRequest.getCantidadProducto());
        planEntregaTerrestreEntity.setFechaRegistro(planEntregaTerrestreRequest.getFechaRegistro());
        planEntregaTerrestreEntity.setPlacaVehiculo(planEntregaTerrestreRequest.getPlacaVehiculo());
        planEntregaTerrestreEntity.setPrecioEnvio(planEntregaTerrestreRequest.getPrecioEnvio());
        planEntregaTerrestreEntity.setNumeroGuia(generateUniqueNumber());
        planEntregaTerrestreEntity.setPrecioEnvioConDescuento(planEntregaTerrestreRequest.getCantidadProducto()>10?planEntregaTerrestreRequest.getPrecioEnvio()-(planEntregaTerrestreRequest.getPrecioEnvio()*0.05):planEntregaTerrestreRequest.getPrecioEnvio());
        PlanEntregaTerrestreEntity planEntregaTerrestreGuardado = planEntregaTerrestreRepository.save(planEntregaTerrestreEntity);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(planEntregaTerrestreGuardado.getNumeroGuia()).toUri();

        return ResponseEntity.created(ubicacion).body(planEntregaTerrestreGuardado);
    }

    public ResponseEntity<PlanEntregaTerrestreEntity> actualizarPlanEntregaTerrestre(PlanEntregaTerrestreRequest planEntregaTerrestre, @PathVariable String numeroGuia) throws GeneralException {
        Optional<PlanEntregaTerrestreEntity> planEntregaMaritimoOptional = planEntregaTerrestreRepository.findById(numeroGuia);
        if(!planEntregaMaritimoOptional.isPresent()){
            throw new GeneralException("plan entrega con numero guia  " + numeroGuia + "  No encontrado", HttpStatus.NOT_FOUND);
        }
        Optional<Cliente> optionalCliente = clienteRepository.findById(planEntregaTerrestre.getClienteId());
        if(!optionalCliente.isPresent()){
            throw new GeneralException("cliente   " + planEntregaTerrestre.getClienteId() + " No existe en db", HttpStatus.BAD_REQUEST);
        }
        Optional<TipoProductoEntity> tipoproductoMaritimoOptional = tipoProductoRepository.findByIdAndClienteAndLogistica(planEntregaTerrestre.getTipoProducto(),optionalCliente.get(),"TERRESTRE");
        if(!tipoproductoMaritimoOptional.isPresent()){
            throw new GeneralException("tipo de producto " + planEntregaTerrestre.getTipoProducto() + "  No esta relacionado al cliente o   No es de tipo MARITIMA", HttpStatus.BAD_REQUEST);
        }
        Optional<LugarAlmacenamientoEntity> bodegaAlmacenamientoMaritimoOptional = lugarAlmacenamientoRepository.findByIdAndClienteAndLogistica(planEntregaTerrestre.getBodegaEntrega(),optionalCliente.get(),"TERRESTRE");
        if(!bodegaAlmacenamientoMaritimoOptional.isPresent()){
            throw new GeneralException("bodega de entrega   " + planEntregaTerrestre.getBodegaEntrega() + "  No esta relacionado al cliente o   No es de tipo MARITIMA", HttpStatus.BAD_REQUEST);
        }

        PlanEntregaTerrestreEntity planEntregaTerrestreEntity = new PlanEntregaTerrestreEntity();
        planEntregaTerrestreEntity.setNumeroGuia(numeroGuia);
        planEntregaTerrestreEntity.setCliente(optionalCliente.get());
        planEntregaTerrestreEntity.setIdDelCliente(optionalCliente.get().getId());
        planEntregaTerrestreEntity.setNombreCliente(optionalCliente.get().getNombre());
        planEntregaTerrestreEntity.setTipoproducto(tipoproductoMaritimoOptional.get().getNombre());
        planEntregaTerrestreEntity.setLugarAlmacenamiento(bodegaAlmacenamientoMaritimoOptional.get().getNombre());
        planEntregaTerrestreEntity.setFechaEntrega(planEntregaTerrestre.getFechaEntrega());
        planEntregaTerrestreEntity.setCantidadProducto(planEntregaTerrestre.getCantidadProducto());
        planEntregaTerrestreEntity.setFechaRegistro(planEntregaTerrestre.getFechaRegistro());
        planEntregaTerrestreEntity.setPlacaVehiculo(planEntregaTerrestre.getPlacaVehiculo());
        planEntregaTerrestreEntity.setPrecioEnvio(planEntregaTerrestre.getPrecioEnvio());
        planEntregaTerrestreEntity.setPrecioEnvioConDescuento(planEntregaTerrestre.getCantidadProducto()>10?planEntregaTerrestre.getPrecioEnvio()-(planEntregaTerrestre.getPrecioEnvio()*0.03):planEntregaTerrestre.getPrecioEnvio());
        PlanEntregaTerrestreEntity planEntregaTerrestreGuardado = planEntregaTerrestreRepository.save(planEntregaTerrestreEntity);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<PlanEntregaTerrestreEntity> eliminarPlanEntregaTerrestre(String numeroGuia) throws GeneralException {
        Optional<PlanEntregaTerrestreEntity> planEntregaTerrestreOptional = planEntregaTerrestreRepository.findById(numeroGuia);

        if(!planEntregaTerrestreOptional.isPresent()){
            throw new GeneralException("plan entrega con numero guia  " + numeroGuia + "  No encontrado", HttpStatus.NOT_FOUND);
        }

        planEntregaTerrestreRepository.delete(planEntregaTerrestreOptional.get());
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<PlanEntregaTerrestreEntity> obtenerPlanEntregaTerrestrePorId(String numeroGuia) throws GeneralException {
        Optional<PlanEntregaTerrestreEntity> planEntregaTerrestreOptional = planEntregaTerrestreRepository.findById(numeroGuia);

        if(!planEntregaTerrestreOptional.isPresent()){
            throw new GeneralException("plan entrega con numero guia  " + numeroGuia + " NO encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(planEntregaTerrestreOptional.get());
    }
}
