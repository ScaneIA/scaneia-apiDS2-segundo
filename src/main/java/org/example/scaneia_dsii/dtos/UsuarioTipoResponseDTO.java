package org.example.scaneia_dsii.dtos;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioTipoResponseDTO {

    private Integer id;
    private String descricao;
    private Boolean ativo;
}
