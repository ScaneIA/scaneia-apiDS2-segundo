<<<<<<<< HEAD:src/main/java/org/example/scaneia_dsii/crud_usuario/DTO/UsuarioRequestDTO.java
package org.example.scaneia_dsii.crud_usuario.DTO;
========
package org.example.scaneia_dsii.dtos;
>>>>>>>> origin:src/main/java/org/example/scaneia_dsii/dtos/UsuarioRequestDTO.java

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.mail.MailException;

public class UsuarioRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
    private String cpf;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String senha;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotNull(message = "id_usuario_tipo é obrigatório")
    private Long idUsuarioTipo;

    @NotNull(message = "id_estrutura é obrigatório")
    private Long idEstrutura;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getIdUsuarioTipo() { return idUsuarioTipo; }
    public void setIdUsuarioTipo(Long idUsuarioTipo) { this.idUsuarioTipo = idUsuarioTipo; }

    public Long getIdEstrutura() { return idEstrutura; }
    public void setIdEstrutura(Long idEstrutura) { this.idEstrutura = idEstrutura; }
}
