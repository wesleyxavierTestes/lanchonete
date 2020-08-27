package com.lanchonete.apllication.dto.combo;

import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    public List<ComboItemDto> bebidas;

    @JsonIgnore
    public List<ComboItemDto> lanches;

    @JsonGetter("lanches")
    public Stream<String> lanches() {
        return lanches.stream().map(lanche -> lanche.nome);
    }

    @JsonGetter("bebidas")
    public Stream<String> bebidas() {
        return bebidas.stream().map(bebida -> bebida.nome);
    }
}
