package com.lanchonete.apllication.dto.produto;

import javax.validation.constraints.NotNull;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto  {

    public long id;

    @NotNull(message =  MessageError.IS_MANDATORY)
    public String nome;

    @NotNull(message =  MessageError.IS_MANDATORY)
    public String valor;

    @NotNull(message =  MessageError.IS_MANDATORY)
    public String custo;

    @NotNull(message =  MessageError.IS_MANDATORY)
    public CategoriaDto categoria;
}
