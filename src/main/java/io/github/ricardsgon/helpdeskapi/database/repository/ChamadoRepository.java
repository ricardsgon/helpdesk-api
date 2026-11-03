package io.github.ricardsgon.helpdeskapi.database.repository;

import io.github.ricardsgon.helpdeskapi.database.model.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    
}
