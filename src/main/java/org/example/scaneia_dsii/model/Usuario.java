package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "usuario")
@Setter
@Getter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(nullable = false, length = 255)
    private String senha;

    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @Column(name = "id_usuario_tipo", nullable = false)
    private Long idUsuarioTipo;

    @Column(name = "id_estrutura", nullable = false)
    private Long idEstrutura;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private Date dataCriacao;

}
