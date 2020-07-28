package com.lanchonete.apllication.dto.lanche;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LancheDto  {

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
    public List<IngredienteDto> ingredientesLanche;
}
