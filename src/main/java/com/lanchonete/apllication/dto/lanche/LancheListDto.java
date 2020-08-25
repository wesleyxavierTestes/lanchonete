package com.lanchonete.apllication.dto.lanche;

import java.util.List;

import javax.validation.constraints.Size;

import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LancheListDto {

    public long id;

    public String nome;
    public String codigo;
    public EnumTipoProduto tipoProduto;
    public String valor;
    public String valorTotal;

    @Size(min = 1, message = MessageError.IS_MANDATORY)
    public List<IngredienteDto> ingredientesLanche;
}
