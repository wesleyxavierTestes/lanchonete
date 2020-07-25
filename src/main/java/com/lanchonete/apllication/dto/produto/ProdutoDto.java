package com.lanchonete.apllication.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto  {

    public long id;
    public String nome;
}
