package br.com.storage_controller.repository;

import br.com.storage_controller.entity.alerta.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    List<Alerta> findByRecursoIdAndResolvidoFalse(Long recursoId);

    List<Alerta> findByResolvidoFalseOrderByDataAlertaDesc();

    List<Alerta> findByRecurso_Setor_IdAndResolvidoFalseOrderByDataAlertaDesc(Long setorId);

    List<Alerta> findByRecurso_Setor_BaseIdAndResolvidoFalseOrderByDataAlertaDesc(Long baseId);
}