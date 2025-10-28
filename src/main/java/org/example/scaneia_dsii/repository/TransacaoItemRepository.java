package org.example.scaneia_dsii.repository;

import org.example.scaneia_dsii.model.TransacaoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoItemRepository extends JpaRepository<TransacaoItem, Long> {
}
