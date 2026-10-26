package com.siip.service_inventario.jar.controller;

import com.siip.service_inventario.jar.dto.AlertaLoteDTO;
import com.siip.service_inventario.jar.service.AlertaService;
import com.siip.service_inventario.jar.service.ReporteExcelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/alertas")
public class AlertaController {

    private final AlertaService alertaService;
    private final ReporteExcelService reporteExcelService;

    public AlertaController(AlertaService alertaService, ReporteExcelService reporteExcelService) {
        this.alertaService = alertaService;
        this.reporteExcelService = reporteExcelService;
    }

    /** GET /api/v1/alertas : lotes que vencen en 90 dias o menos. */
    @GetMapping
    public List<AlertaLoteDTO> listar() {
        return alertaService.listarAlertasFefo();
    }

    /** GET /api/v1/alertas/excel : descarga el mismo reporte en Excel. */
    @GetMapping("/excel")
    public ResponseEntity<byte[]> exportarExcel() throws IOException {
        byte[] archivo = reporteExcelService.generarReporteCaducidades(alertaService.listarAlertasFefo());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=caducidades_fefo.xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(archivo);
    }
}