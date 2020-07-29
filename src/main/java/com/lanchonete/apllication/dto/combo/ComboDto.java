package com.lanchonete.apllication.dto.combo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComboDto  {

    public long id;
    public boolean ativo;

    @NotNull(message = MessageError.IS_MANDATORY)
    public String nome;

    public String codigo;

    @NotNull(message = MessageError.IS_MANDATORY)
    public CategoriaDto categoria;

    @NotNull(message = MessageError.IS_MANDATORY)
    public String valor;

    @NotNull(message = MessageError.IS_MANDATORY)
    public String valorTotal;

    @NotNull(message = MessageError.IS_MANDATORY)
    public ComboBebida bebida;

    @NotNull(message = MessageError.IS_MANDATORY)
    public LancheDto lanche;

    @Size(max = 200, message = MessageError.MAX_LIMITE)
    private String observacao;

}
