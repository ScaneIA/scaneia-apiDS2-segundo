package org.example.scaneia_dsii.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class EstruturaResponseDTO {

    private Integer id;
    private String descricao;
    private Integer estruturaId;
    private Integer estruturaTipoId;
    private Date dataCriacao;
    private Date dataAlteracao;
    private Boolean ativo;

}
