package com.lanchonete.apllication.dto.produto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    public boolean ativo;

    @NotNull(message =  MessageError.IS_MANDATORY)
    @Size(max = 150, message = MessageError.MAX_LIMITE)
    public String nome;

    @NotNull(message =  MessageError.IS_MANDATORY)
    public String valor;

    @NotNull(message =  MessageError.IS_MANDATORY)
    public String custo;

    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String codigo;

    @NotNull(message =  MessageError.IS_MANDATORY)
    public CategoriaDto categoria;
}
