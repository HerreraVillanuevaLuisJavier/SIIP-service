package com.siip.service_inventario.jar.controller;

import com.siip.service_inventario.jar.dto.SucursalDisponibilidadDTO;
import com.siip.service_inventario.jar.service.InventarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Capa Controller (la C de MVC): recibe la petición HTTP, delega al servicio
 * y devuelve JSON. No contiene lógica de negocio.
 */
@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    /**
     * GET /api/v1/inventario/buscar?medicamento=Amoxicilina
     */
    @GetMapping("/buscar")
    public List<SucursalDisponibilidadDTO> buscar(@RequestParam String medicamento) {
        return inventarioService.buscarDisponibilidadCercana(medicamento);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> manejarArgumentoInvalido(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }
}