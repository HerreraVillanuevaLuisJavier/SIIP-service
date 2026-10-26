package com.siip.service_inventario.jar.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.siip.service_inventario.jar.dto.SucursalDisponibilidadDTO;
import com.siip.service_inventario.jar.model.InventarioSucursal;
import com.siip.service_inventario.jar.repository.InventarioSucursalRepository;
import com.siip.service_inventario.jar.util.HaversineUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Logica de negocio de CU01: consulta de inventario georreferenciado.
 * Depende solo de la abstraccion del repositorio (inversion de dependencias,
 * la D de SOLID) y no accede directamente a la base de datos.
 */
@Service
public class InventarioService {

    private static final Logger log = LoggerFactory.getLogger(InventarioService.class);

    // Sede de referencia (San Isidro). Mas adelante vendra de la sucursal del usuario autenticado.
    private static final double LAT_REFERENCIA = -12.0931;
    private static final double LON_REFERENCIA = -77.0465;
    private static final double RADIO_MAXIMO_KM = 10.0;

    private final InventarioSucursalRepository inventarioRepository;

    public InventarioService(InventarioSucursalRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    /**
     * Busca un medicamento por nombre y retorna las sucursales con stock
     * dentro de 10 km, ordenadas de la mas cercana a la mas lejana.
     *
     * @param nombreMedicamento nombre o parte del nombre del medicamento
     * @return sucursales con stock disponible
     */
    public List<SucursalDisponibilidadDTO> buscarDisponibilidadCercana(String nombreMedicamento) {
        // Guava: validacion de argumentos
        Preconditions.checkArgument(
                !Strings.isNullOrEmpty(nombreMedicamento) && !nombreMedicamento.isBlank(),
                "El nombre del medicamento no puede estar vacio");

        // Apache Commons Lang: limpia espacios repetidos
        String termino = StringUtils.normalizeSpace(nombreMedicamento);
        log.info("CU01 - consulta de disponibilidad para '{}'", termino);

        List<InventarioSucursal> resultados =
                inventarioRepository.findByMedicamento_NombreComercialContainingIgnoreCase(termino);

        List<SucursalDisponibilidadDTO> cercanas = resultados.stream()
                .filter(inv -> inv.getStockActual() != null && inv.getStockActual() > 0)
                .map(inv -> {
                    double distancia = HaversineUtil.calcularDistanciaKm(
                            LAT_REFERENCIA, LON_REFERENCIA,
                            inv.getSucursal().getLatitud(), inv.getSucursal().getLongitud());
                    return new SucursalDisponibilidadDTO(
                            inv.getSucursal().getNombre(),
                            inv.getSucursal().getCodigo(),
                            Math.round(distancia * 100) / 100.0,
                            inv.getStockActual());
                })
                .filter(dto -> dto.getDistanciaKm() <= RADIO_MAXIMO_KM)
                .sorted(Comparator.comparing(SucursalDisponibilidadDTO::getDistanciaKm))
                .toList();

        log.debug("CU01 - {} sucursales dentro de {} km", cercanas.size(), RADIO_MAXIMO_KM);
        return cercanas;
    }
}