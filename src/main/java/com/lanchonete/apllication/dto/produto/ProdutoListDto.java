package com.lanchonete.apllication.dto.produto;

import com.lanchonete.domain.enuns.produto.EnumTipoProduto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoListDto {

    public long id;
    public String nome;
    public String valor;
    public String custo;
    public String codigo;
    public EnumTipoProduto tipoProduto;
    public String estoqueAtual;
    public String categoriaNome;
}
