package br.com.storage_controller.repository;

import br.com.storage_controller.entity.base.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository extends JpaRepository<Base, Long> {
}