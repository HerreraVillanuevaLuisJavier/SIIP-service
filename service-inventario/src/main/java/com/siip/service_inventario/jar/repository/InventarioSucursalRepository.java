package com.siip.service_inventario.jar.repository;

import com.siip.service_inventario.jar.model.InventarioSucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventarioSucursalRepository extends JpaRepository<InventarioSucursal, Long> {

    List<InventarioSucursal> findByMedicamento_NombreComercialContainingIgnoreCase(String nombre);
}