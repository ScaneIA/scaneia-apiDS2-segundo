package org.example.scaneia_dsii.dtos;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
@Getter
@Setter
public class UsuarioPerfilResponseDTO {

    private String nome;

    private String email;

    private Date dataCriacao;

    private String cpf;

    public UsuarioPerfilResponseDTO(String nome, String email, Date dataCriacao, String cpf) {
        this.nome = nome;
        this.email = email;
        this.dataCriacao = dataCriacao;
        this.cpf = cpf;
    }
}
