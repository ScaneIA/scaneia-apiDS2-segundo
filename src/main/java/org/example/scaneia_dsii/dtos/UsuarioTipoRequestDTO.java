package org.example.scaneia_dsii.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioTipoRequestDTO {

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 6, message = "Senha deve ter pelo menos 6 caracteres")
    String descricao;

    @NotNull(message = "O status é obrigatório")
    Boolean ativo;
}
