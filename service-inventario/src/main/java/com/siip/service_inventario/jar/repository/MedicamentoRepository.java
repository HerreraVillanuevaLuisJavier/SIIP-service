package com.siip.service_inventario.jar.repository;

import com.siip.service_inventario.jar.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
}