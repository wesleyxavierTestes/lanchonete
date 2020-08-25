package com.lanchonete.domain.entities.produto.processadores;

import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.outros.Outros;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.entities.produto.baseentity.IProduto;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;

public class OutrosProcessaProduto extends ProcessaProduto {

    public OutrosProcessaProduto(IProdutoRepository repository) {
      super(repository);
	}

	@Override
    public <T extends IProduto> T processar(IProduto outros) {
        Produto produto = this.getProduto(outros.getCodigo());
        
        T itemProduto = (T)Mapper.map(produto, Outros.class);
        itemProduto.setTipoProduto(EnumTipoProduto.Outros);
        
        return itemProduto;
    }

    public boolean validarExisteEstoqueProduto(Outros outros) {
        return validarExisteEstoque(outros);
    }
    
}