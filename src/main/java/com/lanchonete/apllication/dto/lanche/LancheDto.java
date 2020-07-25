package com.lanchonete.apllication.dto.lanche;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LancheDto  {

    public long id;
    public String nome;
    public List<IngredienteDto> ingredientes;

}
