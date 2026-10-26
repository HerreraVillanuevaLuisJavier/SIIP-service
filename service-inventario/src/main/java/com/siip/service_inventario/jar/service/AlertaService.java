package com.siip.service_inventario.jar.service;

import com.siip.service_inventario.jar.dto.AlertaLoteDTO;
import com.siip.service_inventario.jar.model.Lote;
import com.siip.service_inventario.jar.repository.LoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Semaforo FEFO (RF-03): lista los lotes que vencen en 90 dias o menos
 * y los clasifica como CRITICO (hasta 30), ALERTA (hasta 60) o PREVENTIVO (hasta 90).
 */
@Service
public class AlertaService {

    private static final Logger log = LoggerFactory.getLogger(AlertaService.class);
    private static final int VENTANA_FEFO_DIAS = 90;

    private final LoteRepository loteRepository;

    public AlertaService(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    public List<AlertaLoteDTO> listarAlertasFefo() {
        LocalDate hoy = LocalDate.now();
        List<Lote> lotes = loteRepository
                .findByFechaVencimientoLessThanEqualOrderByFechaVencimientoAsc(hoy.plusDays(VENTANA_FEFO_DIAS));
        log.info("FEFO: {} lotes dentro de la ventana de {} dias", lotes.size(), VENTANA_FEFO_DIAS);

        return lotes.stream().map(lote -> {
            long dias = ChronoUnit.DAYS.between(hoy, lote.getFechaVencimiento());
            Double precio = lote.getMedicamento().getPrecioUnitario();
            double perdida = (precio == null ? 0.0 : precio) * lote.getCantidadCajas();
            return new AlertaLoteDTO(
                    lote.getCodigoLote(),
                    lote.getMedicamento().getNombreComercial(),
                    lote.getSucursal().getNombre(),
                    lote.getFechaVencimiento(),
                    dias,
                    calcularSemaforo(dias),
                    lote.getCantidadCajas(),
                    perdida);
        }).toList();
    }

    /** Clasifica un lote segun los dias que le quedan antes de vencer. */
    public static String calcularSemaforo(long diasRestantes) {
        if (diasRestantes < 0) return "VENCIDO";
        if (diasRestantes <= 30) return "CRITICO";
        if (diasRestantes <= 60) return "ALERTA";
        return "PREVENTIVO";
    }
}