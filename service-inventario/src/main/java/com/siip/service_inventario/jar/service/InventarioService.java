package com.siip.service_inventario.jar.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.siip.service_inventario.jar.dto.SucursalDisponibilidadDTO;
import com.siip.service_inventario.jar.model.InventarioSucursal;
import com.siip.service_inventario.jar.repository.InventarioSucursalRepository;
import com.siip.service_inventario.jar.util.HaversineUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Lógica de negocio de CU01: consulta de inventario georreferenciado.
 * Depende solo de la abstracción del repositorio (principio de inversión
 * de dependencias, la D de SOLID) y no accede directamente a la base de datos.
 */
@Service
public class InventarioService {

    // Sede de referencia desde donde consulta el químico farmacéutico (San Isidro).
    // En una versión posterior vendrá de la sucursal del usuario autenticado.
    private static final double LAT_REFERENCIA = -12.0931;
    private static final double LON_REFERENCIA = -77.0465;
    private static final double RADIO_MAXIMO_KM = 10.0;

    private final InventarioSucursalRepository inventarioRepository;

    public InventarioService(InventarioSucursalRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    /**
     * Busca un medicamento por nombre y retorna las sucursales con stock
     * dentro de un radio de 10 km, ordenadas de la más cercana a la más lejana.
     *
     * @param nombreMedicamento nombre (o parte del nombre) del medicamento
     * @return lista de sucursales con stock disponible
     */
    public List<SucursalDisponibilidadDTO> buscarDisponibilidadCercana(String nombreMedicamento) {
        // Guava: validación de argumentos
        Preconditions.checkArgument(!Strings.isNullOrEmpty(nombreMedicamento) && !nombreMedicamento.isBlank(),
                "El nombre del medicamento no puede estar vacío");

        List<InventarioSucursal> resultados =
                inventarioRepository.findByMedicamento_NombreComercialContainingIgnoreCase(nombreMedicamento.trim());

        return resultados.stream()
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
    }
}