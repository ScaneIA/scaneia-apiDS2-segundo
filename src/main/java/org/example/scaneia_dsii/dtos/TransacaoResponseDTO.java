package org.example.scaneia_dsii.dtos;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TransacaoResponseDTO {

    private Integer id;
    private Double valorTotal;
    private Integer estrutura_id;
    private Date dataCriacao;
    private Date dataAlteracao;

}
