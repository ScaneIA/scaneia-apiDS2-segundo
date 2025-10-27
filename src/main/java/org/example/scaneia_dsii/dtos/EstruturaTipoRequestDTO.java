package org.example.scaneia_dsii.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class EstruturaTipoRequestDTO {

    @NotNull(message = "O campo ordem é obrigatório")
    @Min(value = 0, message = "O valor da ordem não pode ser menor que 0")
    Integer ordem;

    @NotNull(message = "O campo descrição é obrigatório")
    @NotBlank(message = "O campo descrição não pode ser vazio")
    String descricao;

    @NotNull(message = "O campo ativo é obrigatório")
    Boolean ativo;
}
