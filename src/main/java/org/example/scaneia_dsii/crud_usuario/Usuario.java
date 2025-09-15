package org.example.scaneia_dsii.crud_usuario;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
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
    private LocalDateTime dataCriacao = LocalDateTime.now();

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getIdUsuarioTipo() { return idUsuarioTipo; }
    public void setIdUsuarioTipo(Long idUsuarioTipo) { this.idUsuarioTipo = idUsuarioTipo; }

    public Long getIdEstrutura() { return idEstrutura; }
    public void setIdEstrutura(Long idEstrutura) { this.idEstrutura = idEstrutura; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
}
