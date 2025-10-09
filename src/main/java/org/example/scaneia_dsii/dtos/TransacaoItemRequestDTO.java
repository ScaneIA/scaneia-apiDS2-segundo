package org.example.scaneia_dsii.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoItemRequestDTO {


    @NotNull(message = "A quantidade de planos é obrigatório")
    @Size(min = 1, message = "A quantidade de planos deve ser maior que 0")
    private Integer qtdPlano;

    @NotNull(message = "O ID da estrutura é obrigatório")
    private Integer idTransacao;

    @NotNull(message = "O ID da estrutura é obrigatório")
    private Integer idPlanoEspecificao;

}
