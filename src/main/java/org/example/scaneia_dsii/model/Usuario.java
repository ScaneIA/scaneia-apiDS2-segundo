package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    @Size(max = 255)
    String name;

    @NotNull
    @Size(max = 11)
    String cpf;

    @NotNull
    @Size(max = 255)
    String senha;

    @NotNull
    @Size(max = 255)
    String email;

    @Column(name = "id_tipo_usuario")
    @NotNull
    Integer idTipoUsuario;

    @Column(name = "estrutura_id")
    @NotNull
    Integer estruturaId;

    @Column(name = "data_criacao")
    @NotNull
    Date dataCriacao;

    public Usuario(String name, String cpf, String senha, String email, Integer idTipoUsuario, Integer estruturaId, Date dataCriacao) {
        this.name = name;
        this.cpf = cpf;
        this.senha = senha;
        this.email = email;
        this.idTipoUsuario = idTipoUsuario;
        this.estruturaId = estruturaId;
        this.dataCriacao = dataCriacao;
    }

    public Usuario(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public Integer getEstruturaId() {
        return estruturaId;
    }

    public void setEstruturaId(Integer estruturaId) {
        this.estruturaId = estruturaId;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
