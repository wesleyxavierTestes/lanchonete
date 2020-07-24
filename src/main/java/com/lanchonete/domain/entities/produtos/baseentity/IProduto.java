package com.lanchonete.domain.entities.produtos.baseentity;

public interface IProduto {
    <T extends IProduto> T convert(Class<T> type);
}