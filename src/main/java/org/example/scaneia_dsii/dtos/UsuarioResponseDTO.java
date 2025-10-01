package org.example.scaneia_dsii.dtos;

import java.time.LocalDateTime;
import java.util.Date;

public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private Long idUsuarioTipo;
    private Long idEstrutura;
    private Date dataCriacao;

    public UsuarioResponseDTO(Long id, String nome, String cpf, String email,
                              Long idUsuarioTipo, Long idEstrutura, Date dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.idUsuarioTipo = idUsuarioTipo;
        this.idEstrutura = idEstrutura;
        this.dataCriacao = dataCriacao;
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public Long getIdUsuarioTipo() { return idUsuarioTipo; }
    public Long getIdEstrutura() { return idEstrutura; }
    public Date getDataCriacao() { return dataCriacao; }
}

