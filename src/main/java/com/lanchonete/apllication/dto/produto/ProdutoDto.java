package com.lanchonete.apllication.dto.produto;

import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto  {

    public long id;
    public boolean ativo;

    @NotNull(message =  MessageError.IS_MANDATORY)
    @NotEmpty(message =  MessageError.IS_MANDATORY)
    @Size(max = 150, message = MessageError.MAX_LIMITE)
    public String nome;

    @NotNull(message =  MessageError.IS_MANDATORY)
    @NotEmpty(message =  MessageError.IS_MANDATORY)
    @Size(max = 150, message = MessageError.MAX_LIMITE)
    public String valor;

    @NotNull(message =  MessageError.IS_MANDATORY)
    @NotEmpty(message =  MessageError.IS_MANDATORY)
    @Size(max = 150, message = MessageError.MAX_LIMITE)
    public String custo;

    @Size(max = 150, message = MessageError.MAX_LIMITE)
    @NotNull(message =  MessageError.IS_MANDATORY)
    public String codigo = UUID.randomUUID().toString();

    @NotNull(message =  MessageError.IS_MANDATORY)
    public CategoriaDto categoria;

    @NotNull(message =  MessageError.IS_MANDATORY)
    public EnumTipoProduto tipoProduto;
}
