package com.ingeneo.logistica.controller;

import com.ingeneo.logistica.dto.PlanEntregaTerrestreRequest;
import com.ingeneo.logistica.eception.GeneralException;
import com.ingeneo.logistica.entity.PlanEntregaTerrestreEntity;
import com.ingeneo.logistica.service.PlanEntregaTerrestreService;
import com.ingeneo.logistica.service.impl.PlanEntregaTerrestreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/planes-entrega-terrestre")
public class LogisticaTerrestreController {

	@Autowired
	private PlanEntregaTerrestreService planEntregaTerrestreServiceImpl;


	@GetMapping()
	public List<PlanEntregaTerrestreEntity> searchPlanesEntregaTerrestre(@RequestParam(value = "numeroGuia", required = false) String numeroGuia,
																		 @RequestParam(value = "cantidadProducto", required = false) Integer cantidadProducto,
																		 @RequestParam(value = "fechaRegistro", required = false) String fechaRegistro,
																		 @RequestParam(value = "fechaEntrega", required = false) String fechaEntrega,
																		 @RequestParam(value = "precioEnvio", required = false) Double precioEnvio,
																		 @RequestParam(value = "precioEnvioConDescuento", required = false) Double precioEnvioConDescuento,
																		 @RequestParam(value = "placaVehiculo", required = false) String placaVehiculo,
																		 @RequestParam(value = "tipoproducto", required = false) String tipoproducto,
																		 @RequestParam(value = "lugarAlmacenamiento", required = false) String lugarAlmacenamiento,
																		 @RequestParam(value = "nombreCliente", required = false) String nombreCliente,
																		 @RequestParam(value = "IdCliente", required = false) Integer IdCliente) {
		return planEntregaTerrestreServiceImpl.searchPlanesEntregaTerrestre(numeroGuia, cantidadProducto, fechaRegistro, fechaEntrega, precioEnvio,precioEnvioConDescuento, placaVehiculo ,tipoproducto,lugarAlmacenamiento,nombreCliente,IdCliente);
	}

	@PostMapping
	public ResponseEntity<PlanEntregaTerrestreEntity> guardarPlanEntregaTerrestre(@Valid @RequestBody PlanEntregaTerrestreRequest planEntregaTerrestreRequest) throws GeneralException {
		return planEntregaTerrestreServiceImpl.guardarPlanEntregaTerrestre(planEntregaTerrestreRequest);
	}

	@PutMapping("/{numeroGuia}")
	public ResponseEntity<PlanEntregaTerrestreEntity> actualizarPlanEntregaTerrestre(@Valid @RequestBody PlanEntregaTerrestreRequest planEntregaTerrestre, @PathVariable String numeroGuia) throws GeneralException {
		return planEntregaTerrestreServiceImpl.actualizarPlanEntregaTerrestre(planEntregaTerrestre,numeroGuia);
	}
	
	@DeleteMapping("/{numeroGuia}")
	public ResponseEntity<PlanEntregaTerrestreEntity> eliminarPlanEntregaTerrestre(@PathVariable String numeroGuia) throws GeneralException {
		return planEntregaTerrestreServiceImpl.eliminarPlanEntregaTerrestre(numeroGuia);
	}
	
	@GetMapping("/{numeroGuia}")
	public ResponseEntity<PlanEntregaTerrestreEntity> obtenerPlanEntregaTerrestrePorId(@PathVariable String numeroGuia) throws GeneralException {
		return planEntregaTerrestreServiceImpl.obtenerPlanEntregaTerrestrePorId(numeroGuia);
	}
}
