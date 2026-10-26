package com.siip.service_inventario.jar.service;

import com.siip.service_inventario.jar.dto.AlertaLoteDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/** Exporta el reporte de caducidades a un archivo Excel (.xlsx) con Apache POI. */
@Service
public class ReporteExcelService {

    private static final String[] COLUMNAS = {
            "Lote", "Medicamento", "Sucursal", "Vence", "Dias restantes", "Semaforo", "Cajas", "Perdida (S/)"};

    public byte[] generarReporteCaducidades(List<AlertaLoteDTO> alertas) throws IOException {
        try (Workbook libro = new XSSFWorkbook();
             ByteArrayOutputStream salida = new ByteArrayOutputStream()) {

            Sheet hoja = libro.createSheet("Caducidades FEFO");

            CellStyle estiloEncabezado = libro.createCellStyle();
            Font negrita = libro.createFont();
            negrita.setBold(true);
            estiloEncabezado.setFont(negrita);

            Row encabezado = hoja.createRow(0);
            for (int i = 0; i < COLUMNAS.length; i++) {
                Cell celda = encabezado.createCell(i);
                celda.setCellValue(COLUMNAS[i]);
                celda.setCellStyle(estiloEncabezado);
            }

            int fila = 1;
            for (AlertaLoteDTO a : alertas) {
                Row r = hoja.createRow(fila++);
                r.createCell(0).setCellValue(a.getCodigoLote());
                r.createCell(1).setCellValue(a.getMedicamento());
                r.createCell(2).setCellValue(a.getSucursal());
                r.createCell(3).setCellValue(a.getFechaVencimiento().toString());
                r.createCell(4).setCellValue(a.getDiasRestantes());
                r.createCell(5).setCellValue(a.getSemaforo());
                r.createCell(6).setCellValue(a.getCantidadCajas());
                r.createCell(7).setCellValue(a.getPerdidaEstimada());
            }

            for (int i = 0; i < COLUMNAS.length; i++) {
                hoja.autoSizeColumn(i);
            }
            libro.write(salida);
            return salida.toByteArray();
        }
    }
}