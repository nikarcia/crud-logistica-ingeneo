package com.ingeneo.logistica.controller;

import com.ingeneo.logistica.dto.PlanEntregaMaritimaRequest;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.PlanEntregaMaritimaEntity;
import com.ingeneo.logistica.service.PlanEntregaMaritimaService;
import com.ingeneo.logistica.service.impl.PlanEntregaMaritimaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/planes-entrega-maritima")
public class LogisticaMaritimaController {

	@Autowired
	private PlanEntregaMaritimaService planEntregaMaritimaServiceImpl;


	@GetMapping()
	public List<PlanEntregaMaritimaEntity> searchPlanesEntregaMaritima(@RequestParam(value = "numeroGuia", required = false) String numeroGuia,
																		@RequestParam(value = "cantidadProducto", required = false) Integer cantidadProducto,
																		@RequestParam(value = "fechaRegistro", required = false) String fechaRegistro,
																		@RequestParam(value = "fechaEntrega", required = false) String fechaEntrega,
																		@RequestParam(value = "precioEnvio", required = false) Double precioEnvio,
																	   @RequestParam(value = "precioEnvioConDescuento", required = false) Double precioEnvioConDescuento,
																	   @RequestParam(value = "numeroFlota", required = false) String numeroFlota,
																	   @RequestParam(value = "tipoproducto", required = false) String tipoproducto,
																	   @RequestParam(value = "lugarAlmacenamiento", required = false) String lugarAlmacenamiento,
																	   @RequestParam(value = "nombreCliente", required = false) String nombreCliente,
																	   @RequestParam(value = "IdCliente", required = false) Integer IdCliente) {
		return planEntregaMaritimaServiceImpl.searchPlanesEntregaMaritimo(numeroGuia, cantidadProducto, fechaRegistro, fechaEntrega, precioEnvio,precioEnvioConDescuento, numeroFlota,tipoproducto,lugarAlmacenamiento ,nombreCliente ,IdCliente);
	}

	@GetMapping("/{numeroGuia}")
	public ResponseEntity<PlanEntregaMaritimaEntity> obtenerPlanEntregaMaritimoPorId(@PathVariable String numeroGuia) throws GeneralException {
		return planEntregaMaritimaServiceImpl.obtenerPlanEntregaMaritimoPorId(numeroGuia);
	}

	@PostMapping
	public ResponseEntity<PlanEntregaMaritimaEntity> guardarPlanEntregaMaritimo(@Valid @RequestBody PlanEntregaMaritimaRequest planEntregaMaritimoRequest) throws GeneralException {
		return planEntregaMaritimaServiceImpl.guardarPlanEntregaMaritimo(planEntregaMaritimoRequest);
	}

	@PutMapping("/{numeroGuia}")
	public ResponseEntity<PlanEntregaMaritimaEntity> actualizarPlanEntregaMaritimo(@Valid @RequestBody PlanEntregaMaritimaRequest planEntregaMaritimaRequest, @PathVariable String numeroGuia) throws GeneralException {
		return planEntregaMaritimaServiceImpl.actualizarPlanEntregaMaritimo(planEntregaMaritimaRequest,numeroGuia);
	}

	@DeleteMapping("/{numeroGuia}")
	public ResponseEntity<PlanEntregaMaritimaEntity> eliminarPlanEntregaMaritimo(@PathVariable String numeroGuia) throws GeneralException {
		return planEntregaMaritimaServiceImpl.eliminarPlanEntregaMaritimo(numeroGuia);
	}


}
