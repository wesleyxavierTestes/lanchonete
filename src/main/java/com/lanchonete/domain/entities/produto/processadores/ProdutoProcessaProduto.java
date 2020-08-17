package com.lanchonete.domain.entities.produto.processadores;

import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.entities.produto.baseentity.IProduto;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;

public class ProdutoProcessaProduto extends ProcessaProduto {

    public ProdutoProcessaProduto(IProdutoRepository repository) {
        super(repository);
    }

    @Override
    public <T extends IProduto> T processar(IProduto bebida) {
        Produto produto = this.getProduto(bebida.getCodigo());

        T itemProduto = (T)Mapper.map(produto, Produto.class);
        
        return itemProduto;
    }

    public boolean validarExisteEstoqueProduto(Produto bebida) {
        return validarExisteEstoque(bebida);
    }

}