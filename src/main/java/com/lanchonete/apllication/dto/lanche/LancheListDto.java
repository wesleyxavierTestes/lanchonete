package com.lanchonete.apllication.dto.lanche;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LancheListDto {

    public long id;

    public String nome;
    public String valor;
    public String valorTotal;

    public List<IngredienteDto> ingredientesLanche;
}
