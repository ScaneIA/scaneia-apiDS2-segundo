package org.example.scaneia_dsii.dtos;

import lombok.Data;
import java.util.Date;

@Data
public class PlanoResponseDTO {

    Integer id;

    String descricao;

    Integer qtdPlanilhas;

    Date dataCriacao;

    Date dataAlteracao;

    Boolean ativo;
}
