package com.siip.service_inventario.jar.dto;

import java.time.LocalDate;

/** Una alerta de caducidad FEFO: lote que vence en 90 dias o menos. */
public class AlertaLoteDTO {

    private final String codigoLote;
    private final String medicamento;
    private final String sucursal;
    private final LocalDate fechaVencimiento;
    private final long diasRestantes;
    private final String semaforo;
    private final Integer cantidadCajas;
    private final Double perdidaEstimada;

    public AlertaLoteDTO(String codigoLote, String medicamento, String sucursal,
                         LocalDate fechaVencimiento, long diasRestantes, String semaforo,
                         Integer cantidadCajas, Double perdidaEstimada) {
        this.codigoLote = codigoLote;
        this.medicamento = medicamento;
        this.sucursal = sucursal;
        this.fechaVencimiento = fechaVencimiento;
        this.diasRestantes = diasRestantes;
        this.semaforo = semaforo;
        this.cantidadCajas = cantidadCajas;
        this.perdidaEstimada = perdidaEstimada;
    }

    public String getCodigoLote() { return codigoLote; }
    public String getMedicamento() { return medicamento; }
    public String getSucursal() { return sucursal; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public long getDiasRestantes() { return diasRestantes; }
    public String getSemaforo() { return semaforo; }
    public Integer getCantidadCajas() { return cantidadCajas; }
    public Double getPerdidaEstimada() { return perdidaEstimada; }
}