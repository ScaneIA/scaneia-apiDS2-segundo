package org.example.scaneia_dsii.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class EstruturaRequestDTO {

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    private String descricao;

    @NotNull(message = "O ID da estrutura é obrigatório")
    private Integer estruturaId;

    @NotNull(message = "O ID do tipo da estrutura é obrigatório")
    private Integer estruturaTipoId;

    @NotNull(message = "A data de criação é obrigatória")
    @PastOrPresent(message = "A data de criação não pode passar do dia atual")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataCriacao;

    @NotNull(message = "A data de alteração é obrigatória")
    @PastOrPresent(message = "A data de alteração não pode passar do dia atual")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataAlteracao;

    @NotNull(message = "O status da estrutura")
    private Boolean ativo;

    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}

    public Integer getEstruturaId() {return estruturaId;}
    public void setEstruturaId(Integer estruturaId) {this.estruturaId = estruturaId;}

    public Integer getEstruturaTipoId() {return estruturaTipoId;}
    public void setEstruturaTipoId(Integer estruturaTipoId) {this.estruturaTipoId = estruturaTipoId;}

    public Date getDataCriacao() {return dataCriacao;}
    public void setDataCriacao(Date dataCriacao) {this.dataCriacao = dataCriacao;}

    public Date getDataAlteracao() {return dataAlteracao;}
    public void setDataAlteracao(Date dataAlteracao) {this.dataAlteracao = dataAlteracao;}

    public Boolean getAtivo() {return ativo;}
    public void setAtivo(Boolean ativo) {this.ativo = ativo;}

}
