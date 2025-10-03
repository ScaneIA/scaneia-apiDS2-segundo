package org.example.scaneia_dsii.dtos;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class UsuarioResponseDTO {

    private Long id;

    private String nome;

    private String cpf;

    private String email;

    private Long idUsuarioTipo;

    private Long idEstrutura;

    private Date dataCriacao;

}

