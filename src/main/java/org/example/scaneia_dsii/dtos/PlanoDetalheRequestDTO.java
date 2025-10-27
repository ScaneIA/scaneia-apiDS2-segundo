package org.example.scaneia_dsii.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PlanoDetalheRequestDTO {
    @NotNull(message = "O campo preço é obrigatório")
    @Digits(integer = 3, fraction = 2, message = "O preço deve ter no máximo 3 dígitos inteiros e 2 casas decimais")
    @DecimalMin(value = "0.00", inclusive = false, message = "O preço deve ser maior que zero")
    Double preco;

    @NotNull(message = "O campo qtd_meses é obrigatório")
    @Size(min = 0, message = "A quantidade mínima de meses é 0")
    Integer qtdMeses;

    @NotNull(message = "O campo id_plano é obrigatório")
    Integer planoId;
}
