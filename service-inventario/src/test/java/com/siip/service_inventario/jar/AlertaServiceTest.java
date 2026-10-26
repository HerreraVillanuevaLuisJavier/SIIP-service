package com.siip.service_inventario.jar;

import com.siip.service_inventario.jar.service.AlertaService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlertaServiceTest {

    @Test
    void clasificaElSemaforoFefoSegunLosDiasRestantes() {
        assertEquals("VENCIDO", AlertaService.calcularSemaforo(-1));
        assertEquals("CRITICO", AlertaService.calcularSemaforo(18));
        assertEquals("CRITICO", AlertaService.calcularSemaforo(30));
        assertEquals("ALERTA", AlertaService.calcularSemaforo(42));
        assertEquals("PREVENTIVO", AlertaService.calcularSemaforo(75));
    }
}