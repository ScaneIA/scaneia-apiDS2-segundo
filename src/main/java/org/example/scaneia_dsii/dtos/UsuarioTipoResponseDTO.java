package org.example.scaneia_dsii.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioTipoResponseDTO {
    private Long id;
    private String descricao;
    private Boolean ativo;
}
