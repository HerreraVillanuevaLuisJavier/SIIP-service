package com.siip.service_inventario.jar;

import com.siip.service_inventario.jar.util.HaversineUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HaversineUtilTest {

    @Test
    void distanciaEntreMismoPuntoEsCero() {
        assertEquals(0.0, HaversineUtil.calcularDistanciaKm(-12.09, -77.04, -12.09, -77.04), 0.0001);
    }

    @Test
    void sanIsidroAMirafloresEstaDentroDeLosDiezKm() {
        double km = HaversineUtil.calcularDistanciaKm(-12.0931, -77.0465, -12.1211, -77.0282);
        assertTrue(km > 2 && km < 10, "Distancia inesperada: " + km);
    }
}