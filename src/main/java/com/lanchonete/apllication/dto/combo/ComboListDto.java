package com.lanchonete.apllication.dto.combo;

import com.lanchonete.domain.enuns.produto.EnumTipoProduto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComboListDto {

    public long id;
    public String nome;
    public String valor;
    public String codigo;
    public EnumTipoProduto tipoProduto;
    public String valorTotal;
    public String lancheNome;
    public String bebidaNome;
}
