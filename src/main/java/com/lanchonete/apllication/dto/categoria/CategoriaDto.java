package com.lanchonete.apllication.dto.categoria;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDto  {

    public long id;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Size(max = 150, message = MessageError.MAX_LIMITE)
    public String nome;
}
