package com.lanchonete.domain.entities.produto.processadores;

import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.combo.Combo;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.entities.produto.baseentity.IProduto;
import com.lanchonete.infra.repositorys.produto.IAbstractProdutoRepository;

public class ComboProcessaProduto extends ProcessaProduto {

    public ComboProcessaProduto(IAbstractProdutoRepository repository) {
      super(repository);
	}

	@Override
    public <T extends IProduto> T processar(IProduto combo) {
        Produto produto = this.getProduto(combo.getCodigo());
        
        T itemProduto = (T)Mapper.map(produto, Combo.class);
        
        return (T)itemProduto;
    }

    @Override
    public <T extends IProduto> T save(IProduto produto) {
        this._repository.save((Combo)produto);
        return null;
    }

    public boolean validarExisteEstoqueProduto(Combo combo) {
        boolean existeEstoqueBebida = new BebidaProcessaProduto(this._repository).validarExisteEstoqueProduto(combo.getBebida());
        boolean existeEstoqueLanche = new LancheProcessaProduto(this._repository).validarExisteEstoqueProduto(combo.getLanche());
        return existeEstoqueBebida && existeEstoqueLanche;
    }
}