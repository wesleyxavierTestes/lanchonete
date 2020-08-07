package com.lanchonete.domain.entities.produto.baseentity;

import java.util.UUID;

import com.lanchonete.domain.entities.categoria.Categoria;

public interface IProdutoComposicao extends IProduto {
    long getId();
    void setId(long id);
    String getNome();
	void setCategoria(Categoria categoria);
	void setCodigo(UUID codigo);
}