package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "transacao")
@Setter
@Getter
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "valor_total")
    Double valorTotal;

    @Column(name = "estrutura_id")
    Integer estrutura_id;

    @Column(name = "data_criacao")
    Date dataCriacao;

    @Column(name = "data_alteracao")
    Date dataAlteracao;

}
