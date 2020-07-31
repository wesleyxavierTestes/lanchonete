package com.lanchonete.domain.entities.produto.processadores;

import java.util.List;
import java.util.UUID;

import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.entities.produto.baseentity.IProduto;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;

public abstract class ProcessaProduto {
    public abstract <T extends IProduto> T processar(IProduto produto);


    protected final IProdutoRepository _repository;

    public ProcessaProduto(IProdutoRepository repository) {
        _repository = repository;
    }

    protected boolean validarExisteEstoque(IProduto produtoCardapio) {
        UUID codigo = produtoCardapio.getCodigo();
        long id = getProduto(codigo).getId();
        double estoqueAtual = this._repository.countEstoqueById(id);
        return (estoqueAtual > 0);
    }

    protected Produto getProduto(UUID codigo) {
        Produto produtoExample = new Produto();
        produtoExample.setCodigo(codigo);

        Example<Produto> example = Example.of(produtoExample,
                ExampleMatcher.matching().withIgnoreCase().withIgnorePaths("id").withIgnorePaths("dataCadastro")
                        .withIgnorePaths("ativo").withIgnoreNullValues().withStringMatcher(StringMatcher.CONTAINING));

        List<Produto> lista = this._repository.findAll(example);

        if (lista.isEmpty())
            throw new RegraNegocioException("Código de produto inexistente");

        return lista.get(0);
    }

}