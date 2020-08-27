package com.lanchonete.apllication.dto.estoque;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueListDto {

    public long id;
    public long quantidade;
    public String dataCadastro;

    @JsonIgnore
    public EstoqueProdutoDto produto;

    @JsonGetter("produtoNome")
    public String produtoNome() {
        return this.produto.nome;
    }

}
