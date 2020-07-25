package com.lanchonete.apllication.dto.combo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComboDto  {

    public long id;
    public String nome;
    public List<ComposicaoDto> composicao;
}
