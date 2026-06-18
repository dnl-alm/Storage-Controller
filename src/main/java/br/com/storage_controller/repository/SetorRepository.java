package br.com.storage_controller.repository;

import br.com.storage_controller.entity.setor.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {

    List<Setor> findByBaseId(Long baseId);
}