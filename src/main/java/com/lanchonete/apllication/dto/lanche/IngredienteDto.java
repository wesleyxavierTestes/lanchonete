package com.lanchonete.apllication.dto.lanche;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredienteDto {

    public long id;
    public boolean ativo;
    @Max(value = 30, message = MessageError.MAX_LIMITE)
    public String dataCadastro;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String nome;

    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String codigo;

    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String valor;   
}