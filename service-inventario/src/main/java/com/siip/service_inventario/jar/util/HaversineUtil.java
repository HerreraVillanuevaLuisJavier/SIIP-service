package com.siip.service_inventario.jar.util;

/**
 * Calcula la distancia geodésica entre dos coordenadas usando la fórmula
 * de Haversine. Se usa para encontrar sucursales cercanas (menos de 10 km) en CU01.
 */
public class HaversineUtil {

    private static final double RADIO_TIERRA_KM = 6371.0;

    private HaversineUtil() {
        // Clase utilitaria: no se instancia
    }

    /**
     * @param lat1 latitud del punto de origen
     * @param lon1 longitud del punto de origen
     * @param lat2 latitud del punto destino
     * @param lon2 longitud del punto destino
     * @return distancia en kilómetros
     */
    public static double calcularDistanciaKm(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIO_TIERRA_KM * c;
    }
}