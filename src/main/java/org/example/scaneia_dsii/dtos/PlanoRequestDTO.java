package org.example.scaneia_dsii.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.scaneia_dsii.validation.OnCreate;
import org.example.scaneia_dsii.validation.OnPatch;

@Getter
@Setter
public class PlanoRequestDTO {
    @NotBlank
    @NotNull(message = "O campo descrição é obrigatório", groups = {OnCreate.class, OnPatch.class})
    @Size(max = 255, message = "No máximo 255 caracteres", groups = {OnCreate.class, OnPatch.class})
    String descricao;

    @NotNull(message = "O campo quantidade de planilhas é obrigatório", groups = {OnCreate.class, OnPatch.class})
    @Size(min = 0, message = "A quantidade mínima de planilhas é 0")
    Integer qtdPlanilhas;

    @NotNull(message = "O campo ativo é obrigatório", groups = {OnCreate.class, OnPatch.class})
    Boolean ativo;
}
