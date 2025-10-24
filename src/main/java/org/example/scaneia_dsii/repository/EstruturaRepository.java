package org.example.scaneia_dsii.repository;

import org.example.scaneia_dsii.model.Estrutura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstruturaRepository extends JpaRepository<Estrutura, Long> {
}
