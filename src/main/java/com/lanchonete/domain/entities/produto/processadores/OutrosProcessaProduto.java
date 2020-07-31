package com.lanchonete.domain.entities.produto.processadores;

import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.outros.Outros;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.entities.produto.baseentity.IProduto;
import com.lanchonete.infra.repositorys.produto.IAbstractProdutoRepository;

public class OutrosProcessaProduto extends ProcessaProduto {

    public OutrosProcessaProduto(IAbstractProdutoRepository repository) {
      super(repository);
	}

	@Override
    public <T extends IProduto> T processar(IProduto outros) {
        Produto produto = this.getProduto(outros.getCodigo());
        
        T itemProduto = (T)Mapper.map(produto, Outros.class);
        
        return (T)itemProduto;
    }

    @Override
    public <T extends IProduto> T save(IProduto produto) {
        this._repository.save((Outros)produto);
        return null;
    }

    public boolean validarExisteEstoqueProduto(Outros outros) {
        return validarExisteEstoque(outros);
    }
    
}