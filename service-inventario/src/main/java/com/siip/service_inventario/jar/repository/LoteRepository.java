package com.siip.service_inventario.jar.repository;

import com.siip.service_inventario.jar.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Long> {

    List<Lote> findByFechaVencimientoLessThanEqualOrderByFechaVencimientoAsc(LocalDate limite);
}