package com.siip.service_inventario.jar;

import com.siip.service_inventario.jar.dto.SucursalDisponibilidadDTO;
import com.siip.service_inventario.jar.model.InventarioSucursal;
import com.siip.service_inventario.jar.model.Sucursal;
import com.siip.service_inventario.jar.repository.InventarioSucursalRepository;
import com.siip.service_inventario.jar.service.InventarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioSucursalRepository repositorio;

    @InjectMocks
    private InventarioService servicio;

    @Test
    void retornaSoloSucursalesConStockDentroDelRadioOrdenadasPorCercania() {
        InventarioSucursal miraflores = inventario("Mifarma Miraflores", "MIF-002", -12.1211, -77.0282, 42);
        InventarioSucursal sanBorja = inventario("InkaFarma San Borja", "INK-003", -12.1080, -76.9990, 60);
        InventarioSucursal sinStock = inventario("InkaFarma San Isidro", "INK-001", -12.0950, -77.0350, 0);
        InventarioSucursal lejana = inventario("InkaFarma Ate", "INK-005", -12.0260, -76.9170, 85);

        when(repositorio.findByMedicamento_NombreComercialContainingIgnoreCase("amoxicilina"))
                .thenReturn(List.of(sanBorja, lejana, sinStock, miraflores));

        List<SucursalDisponibilidadDTO> resultado = servicio.buscarDisponibilidadCercana("amoxicilina");

        assertEquals(2, resultado.size());
        assertEquals("MIF-002", resultado.get(0).getCodigoSucursal());
        assertEquals("INK-003", resultado.get(1).getCodigoSucursal());
    }

    @Test
    void rechazaUnNombreDeMedicamentoVacio() {
        assertThrows(IllegalArgumentException.class, () -> servicio.buscarDisponibilidadCercana("   "));
    }

    private InventarioSucursal inventario(String nombre, String codigo, double lat, double lon, int stock) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(nombre);
        sucursal.setCodigo(codigo);
        sucursal.setLatitud(lat);
        sucursal.setLongitud(lon);

        InventarioSucursal inv = new InventarioSucursal();
        inv.setSucursal(sucursal);
        inv.setStockActual(stock);
        return inv;
    }
}