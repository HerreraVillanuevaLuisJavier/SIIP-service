package com.siip.service_inventario.jar.dto;

/**
 * Objeto de transferencia que viaja como JSON hacia el frontend en CU01:
 * una sucursal con stock disponible del medicamento buscado.
 */
public class SucursalDisponibilidadDTO {

    private String nombreSucursal;
    private String codigoSucursal;
    private Double distanciaKm;
    private Integer stockDisponible;

    public SucursalDisponibilidadDTO(String nombreSucursal, String codigoSucursal,
                                     Double distanciaKm, Integer stockDisponible) {
        this.nombreSucursal = nombreSucursal;
        this.codigoSucursal = codigoSucursal;
        this.distanciaKm = distanciaKm;
        this.stockDisponible = stockDisponible;
    }

    public String getNombreSucursal() { return nombreSucursal; }
    public String getCodigoSucursal() { return codigoSucursal; }
    public Double getDistanciaKm() { return distanciaKm; }
    public Integer getStockDisponible() { return stockDisponible; }
}