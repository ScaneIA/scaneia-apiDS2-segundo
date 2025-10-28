package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transacao_item")
@Setter
@Getter
public class TransacaoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "qtd_plano")
    Integer qtdPlano;

    @Column(name = "id_transacao")
    Integer idTransacao;

    @Column(name = "id_plano_especificao")
    Integer idPlanoEspecificao;

}
