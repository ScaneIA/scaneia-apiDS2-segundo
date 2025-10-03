package org.example.scaneia_dsii.dtos;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class PlanoDetalheResponseDTO {
    Long id;

    Double preco;

    Integer qtdMeses;

    Integer planoId;

    Date dataCriacao;
}
