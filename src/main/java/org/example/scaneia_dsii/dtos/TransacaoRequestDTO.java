package org.example.scaneia_dsii.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransacaoRequestDTO {

    @NotNull(message = "O valor total é obrigatório")
    @Size(min = 1, message = "O valor total deve ser maior que 0")
    Double valorTotal;

    @NotNull(message = "O ID da estrutura é obrigatório")
    Integer estrutura_id;

    @NotNull(message = "A data de criação é obrigatória")
    @PastOrPresent(message = "A data de criação não pode passar do dia atual")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date dataCriacao;

    @NotNull(message = "A data de alteração é obrigatória")
    @PastOrPresent(message = "A data de alteração não pode passar do dia atual")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date dataAlteracao;
}
