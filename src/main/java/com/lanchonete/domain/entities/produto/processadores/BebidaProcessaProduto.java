package com.lanchonete.domain.entities.produto.processadores;

import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.bebida.Bebida;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.entities.produto.baseentity.IProduto;
import com.lanchonete.infra.repositorys.produto.IAbstractProdutoRepository;

public class BebidaProcessaProduto extends ProcessaProduto {

    public BebidaProcessaProduto(IAbstractProdutoRepository repository) {
        super(repository);
    }

    @Override
    public <T extends IProduto> T processar(IProduto bebida) {
        Produto produto = this.getProduto(bebida.getCodigo());

        T itemProduto = (T)Mapper.map(produto, Bebida.class);
        
        return (T)itemProduto;
    }

    public boolean validarExisteEstoqueProduto(Bebida bebida) {
        return validarExisteEstoque(bebida);
    }

    @Override
    public <T extends IProduto> T save(IProduto produto) {
        this._repository.save((Bebida)produto);
        return null;
    }

}