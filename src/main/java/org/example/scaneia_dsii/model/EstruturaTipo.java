package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EstruturaTipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    Integer ordem;

    @Column(nullable = false, length = 127)
    String descricao;

    @Column(nullable = false)
    Boolean ativo;

    public EstruturaTipo(Integer id, Integer ordem, String descricao, Boolean ativo) {
        this.id = id;
        this.ordem = ordem;
        this.descricao = descricao;
        this.ativo = ativo;
    }
    public EstruturaTipo() {

    }
}
