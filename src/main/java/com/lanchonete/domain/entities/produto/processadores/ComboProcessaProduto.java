package com.lanchonete.domain.entities.produto.processadores;

import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.combo.Combo;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.entities.produto.baseentity.IProduto;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
import com.lanchonete.infra.repositorys.combo.IComboRepository;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;

public class ComboProcessaProduto extends ProcessaProduto {

    public ComboProcessaProduto(IProdutoRepository repository) {
        super(repository);
    }

    @Override
    public <T extends IProduto> T processar(IProduto combo) {
        Produto produto = this.getProduto(combo.getCodigo());

        T itemProduto = (T) Mapper.map(produto, Combo.class);
        itemProduto.setTipoProduto(EnumTipoProduto.Combo);

        return (T) itemProduto;
    }

    public <T extends IProduto> T processar(IComboRepository _lancheRepository, IProduto combo) {
        Combo produto = _lancheRepository.findByCodigo(combo.getCodigo());

        T itemProduto = (T) Mapper.map(produto, Combo.class);

        return (T) itemProduto;
    }

    public boolean validarExisteEstoqueProduto(Combo combo) {
        boolean existeEstoqueBebida = new BebidaProcessaProduto(this._repository)
                .validarExisteEstoqueProduto(combo.getBebida());
        boolean existeEstoqueLanche = new LancheProcessaProduto(this._repository)
                .validarExisteEstoqueProduto(combo.getLanche());
        return existeEstoqueBebida && existeEstoqueLanche;
    }
}