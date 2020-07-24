package com.lanchonete.domain.entities.produto.baseentity;

public interface IProduto {
    <T extends IProduto> T convert(Class<T> type);
}