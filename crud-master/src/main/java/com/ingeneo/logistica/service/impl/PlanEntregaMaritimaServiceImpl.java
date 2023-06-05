package com.ingeneo.logistica.service.impl;

import com.ingeneo.logistica.dto.PlanEntregaMaritimaRequest;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.Cliente;
import com.ingeneo.logistica.entity.LugarAlmacenamientoEntity;
import com.ingeneo.logistica.entity.PlanEntregaMaritimaEntity;
import com.ingeneo.logistica.entity.TipoProductoEntity;
import com.ingeneo.logistica.repository.*;
import com.ingeneo.logistica.service.PlanEntregaMaritimaService;
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
public class PlanEntregaMaritimaServiceImpl implements PlanEntregaMaritimaService {

    @Autowired
    private PlanEntregaMaritimaRepository planEntregaMaritimaRepository;

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
    public List<PlanEntregaMaritimaEntity> searchPlanesEntregaMaritimo(String numeroGuia, Integer cantidadProducto, String fechaRegistro,
                                                                       String fechaEntrega, Double precioEnvio,Double precioEnvioConDescuento, String placaVehiculo , String tipoProducto , String lugarAlmacenamiento, String nombreCliente, Integer idCliente) {
        return planEntregaMaritimaRepository.search(numeroGuia, cantidadProducto, fechaRegistro, fechaEntrega, precioEnvio,precioEnvioConDescuento, placaVehiculo, tipoProducto ,lugarAlmacenamiento , nombreCliente , idCliente);
    }

    public ResponseEntity<PlanEntregaMaritimaEntity> guardarPlanEntregaMaritimo(PlanEntregaMaritimaRequest planEntregaMaritimaRequest) throws GeneralException {
        Optional<Cliente> optionalCliente = clienteRepository.findById(planEntregaMaritimaRequest.getClienteId());

        if(!optionalCliente.isPresent()){
            throw new GeneralException("cliente   " + planEntregaMaritimaRequest.getClienteId() + " No existe en db", HttpStatus.BAD_REQUEST);
        }
        Optional<TipoProductoEntity> tipoproductoMaritimoOptional = tipoProductoRepository.findByIdAndClienteAndLogistica(planEntregaMaritimaRequest.getTipoProducto(),optionalCliente.get(),"MARITIMA");
        if(!tipoproductoMaritimoOptional.isPresent()){
            throw new GeneralException("tipo de producto " + planEntregaMaritimaRequest.getTipoProducto() + " NO esta relacionado al cliente o   No es de tipo MARITIMA", HttpStatus.BAD_REQUEST);
        }
        Optional<LugarAlmacenamientoEntity> bodegaAlmacenamientoMaritimoOptional = lugarAlmacenamientoRepository.findByIdAndClienteAndLogistica(planEntregaMaritimaRequest.getPuertoEntrega(),optionalCliente.get(),"MARITIMA");
        if(!bodegaAlmacenamientoMaritimoOptional.isPresent()){
            throw new GeneralException("puerto de entrega   " + planEntregaMaritimaRequest.getPuertoEntrega() + "  No esta relacionado al cliente o   No es de tipo MARITIMA", HttpStatus.BAD_REQUEST);
        }

        PlanEntregaMaritimaEntity PlanEntregaMaritimaEntity = new PlanEntregaMaritimaEntity();
        PlanEntregaMaritimaEntity.setCliente(optionalCliente.get());
        PlanEntregaMaritimaEntity.setIdCliente(optionalCliente.get().getId());
        PlanEntregaMaritimaEntity.setNombreCliente(optionalCliente.get().getNombre());
        PlanEntregaMaritimaEntity.setTipoProducto(tipoproductoMaritimoOptional.get().getNombre());
        PlanEntregaMaritimaEntity.setPuertoEntrega(bodegaAlmacenamientoMaritimoOptional.get().getNombre());
        PlanEntregaMaritimaEntity.setFechaEntrega(planEntregaMaritimaRequest.getFechaEntrega());
        PlanEntregaMaritimaEntity.setCantidadProducto(planEntregaMaritimaRequest.getCantidadProducto());
        PlanEntregaMaritimaEntity.setFechaRegistro(planEntregaMaritimaRequest.getFechaRegistro());
        PlanEntregaMaritimaEntity.setNumeroFlota(planEntregaMaritimaRequest.getNumeroFlota());
        PlanEntregaMaritimaEntity.setPrecioEnvio(planEntregaMaritimaRequest.getPrecioEnvio());
        PlanEntregaMaritimaEntity.setNumeroGuia(generateUniqueNumber());
        PlanEntregaMaritimaEntity.setPrecioEnvioConDescuento(planEntregaMaritimaRequest.getCantidadProducto()>10?planEntregaMaritimaRequest.getPrecioEnvio()-(planEntregaMaritimaRequest.getPrecioEnvio()*0.03):planEntregaMaritimaRequest.getPrecioEnvio());
        PlanEntregaMaritimaEntity planEntregaMaritimaGuardado = planEntregaMaritimaRepository.save(PlanEntregaMaritimaEntity);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(planEntregaMaritimaGuardado.getNumeroGuia()).toUri();

        return ResponseEntity.created(ubicacion).body(planEntregaMaritimaGuardado);
    }

    public ResponseEntity<PlanEntregaMaritimaEntity> actualizarPlanEntregaMaritimo(PlanEntregaMaritimaRequest planEntregaMaritimaRequest, @PathVariable String numeroGuia) throws GeneralException {
        Optional<PlanEntregaMaritimaEntity> planEntregaMaritimoOptional = planEntregaMaritimaRepository.findById(numeroGuia);
        if(!planEntregaMaritimoOptional.isPresent()){
            throw new GeneralException("plan entrega con numero guia  " + numeroGuia + "  No encontrado", HttpStatus.NOT_FOUND);
        }
        Optional<Cliente> optionalCliente = clienteRepository.findById(planEntregaMaritimaRequest.getClienteId());
        if(!optionalCliente.isPresent()){
            throw new GeneralException("cliente   " + planEntregaMaritimaRequest.getClienteId() + " No existe en db", HttpStatus.BAD_REQUEST);
        }
        Optional<TipoProductoEntity> tipoproductoMaritimoOptional = tipoProductoRepository.findByIdAndClienteAndLogistica(planEntregaMaritimaRequest.getTipoProducto(),optionalCliente.get(),"MARITIMA");
        if(!tipoproductoMaritimoOptional.isPresent()){
            throw new GeneralException("tipo de producto " + planEntregaMaritimaRequest.getTipoProducto() + "  No esta relacionado al cliente o   No es de tipo MARITIMA", HttpStatus.BAD_REQUEST);
        }
        Optional<LugarAlmacenamientoEntity> bodegaAlmacenamientoMaritimoOptional = lugarAlmacenamientoRepository.findByIdAndClienteAndLogistica(planEntregaMaritimaRequest.getPuertoEntrega(),optionalCliente.get(),"MARITIMA");
        if(!bodegaAlmacenamientoMaritimoOptional.isPresent()){
            throw new GeneralException("puerto de entrega   " + planEntregaMaritimaRequest.getPuertoEntrega() + "  No esta relacionado al cliente o   No es de tipo MARITIMA", HttpStatus.BAD_REQUEST);
        }

        PlanEntregaMaritimaEntity PlanEntregaMaritimaEntity = new PlanEntregaMaritimaEntity();
        PlanEntregaMaritimaEntity.setNumeroGuia(numeroGuia);
        PlanEntregaMaritimaEntity.setCliente(optionalCliente.get());
        PlanEntregaMaritimaEntity.setIdCliente(optionalCliente.get().getId());
        PlanEntregaMaritimaEntity.setNombreCliente(optionalCliente.get().getNombre());
        PlanEntregaMaritimaEntity.setTipoProducto(tipoproductoMaritimoOptional.get().getNombre());
        PlanEntregaMaritimaEntity.setPuertoEntrega(bodegaAlmacenamientoMaritimoOptional.get().getNombre());
        PlanEntregaMaritimaEntity.setFechaEntrega(planEntregaMaritimaRequest.getFechaEntrega());
        PlanEntregaMaritimaEntity.setCantidadProducto(planEntregaMaritimaRequest.getCantidadProducto());
        PlanEntregaMaritimaEntity.setFechaRegistro(planEntregaMaritimaRequest.getFechaRegistro());
        PlanEntregaMaritimaEntity.setNumeroFlota(planEntregaMaritimaRequest.getNumeroFlota());
        PlanEntregaMaritimaEntity.setPrecioEnvio(planEntregaMaritimaRequest.getPrecioEnvio());
        PlanEntregaMaritimaEntity.setPrecioEnvioConDescuento(planEntregaMaritimaRequest.getCantidadProducto()>10?planEntregaMaritimaRequest.getPrecioEnvio()-(planEntregaMaritimaRequest.getPrecioEnvio()*0.03):planEntregaMaritimaRequest.getPrecioEnvio());
        PlanEntregaMaritimaEntity planEntregaMaritimaGuardado = planEntregaMaritimaRepository.save(PlanEntregaMaritimaEntity);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<PlanEntregaMaritimaEntity> eliminarPlanEntregaMaritimo(String numeroGuia) throws GeneralException {
        Optional<PlanEntregaMaritimaEntity> planEntregaMaritimoOptional = planEntregaMaritimaRepository.findById(numeroGuia);
        if(!planEntregaMaritimoOptional.isPresent()){
            throw new GeneralException("plan entrega con numero guia  " + numeroGuia + "  No encontrado", HttpStatus.NOT_FOUND);
        }
        planEntregaMaritimaRepository.delete(planEntregaMaritimoOptional.get());
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<PlanEntregaMaritimaEntity> obtenerPlanEntregaMaritimoPorId(String numeroGuia) throws GeneralException {
        Optional<PlanEntregaMaritimaEntity> planEntregaMaritimoOptional = planEntregaMaritimaRepository.findById(numeroGuia);

        if(!planEntregaMaritimoOptional.isPresent()){
            throw new GeneralException("plan entrega con numero guia  " + numeroGuia + " NO encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(planEntregaMaritimoOptional.get());
    }
}
