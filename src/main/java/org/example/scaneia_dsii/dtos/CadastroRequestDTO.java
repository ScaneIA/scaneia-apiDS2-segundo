package org.example.scaneia_dsii.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroRequestDTO {
    @NotNull(message = "O campo email não pode ser nulo")
    @NotBlank(message = "O campo email é obrigatório")
    String email;

    @NotNull(message = "O campo email não pode ser nulo")
    @NotBlank(message = "O campo email é obrigatório")
    String senha;
}
