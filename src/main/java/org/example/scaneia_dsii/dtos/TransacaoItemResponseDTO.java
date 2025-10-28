package org.example.scaneia_dsii.dtos;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransacaoItemResponseDTO {

    private Integer id;
    private Integer qtdPlano;
    private Integer idTransacao;
    private Integer idPlanoEspecificao;
}
