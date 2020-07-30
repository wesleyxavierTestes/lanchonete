package com.lanchonete.domain.services.cardapio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.lanchonete.apllication.dto.cardapio.CardapioListDto;
import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.entities.combo.Combo;
import com.lanchonete.domain.entities.lanche.Ingrediente;
import com.lanchonete.domain.entities.lanche.Lanche;
import com.lanchonete.domain.entities.produto.baseentity.IProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoComposicao;
import com.lanchonete.domain.entities.produto.entities.Produto;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.cardapio.ICardapioRepository;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

@Service
public class CardapioService extends BaseService<Cardapio> {

    private final ICardapioRepository _repository;

    @Autowired
    private IProdutoRepository _produtoRepository;

    @Autowired
    public CardapioService(ICardapioRepository repository) {
        super(repository);
        _repository = repository;
    }

    public Page<CardapioListDto> listFilterDto(Cardapio entity, int page) {
        return super.listFilter(entity, page).map(filterCardapio()).map(Mapper.pageMap(CardapioListDto.class));
    }

    private Function<? super Cardapio, ? extends Cardapio> filterCardapio() {
        return cardapio -> {
            Set<IProdutoCardapio> getItensDisponiveis = new HashSet<>();
            filterProduto(cardapio, getItensDisponiveis);
            cardapio.setItensDisponiveis(getItensDisponiveis);
            return cardapio;
        };
    }

    private void filterProduto(Cardapio cardapio, Set<IProdutoCardapio> getItensDisponiveis) {
        List<IProdutoCardapio> itensDisponiveis = new ArrayList<>(cardapio.getItensDisponiveis());

        for (IProdutoCardapio produtoCardapio : itensDisponiveis) {
            boolean valido = false;
            try {
                if (produtoCardapio instanceof Lanche) {
                    valido = validarExisteEstoqueProdutoLanche((Lanche) produtoCardapio);
                } else if (produtoCardapio instanceof Combo) {
                    valido = validarExisteEstoqueProdutoCombo((Combo) produtoCardapio);
                } else {
                    UUID codigo = produtoCardapio.getCodigo();
                    valido = validarExisteEstoqueProduto(codigo);
                }

                if (valido) {
                    getItensDisponiveis.add(produtoCardapio);
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private boolean validarExisteEstoqueProdutoCombo(Combo combo) {
        boolean existeEstoqueBebida = validarExisteEstoqueProduto(combo.getBebida().getCodigo());
        boolean existeEstoqueLanche = validarExisteEstoqueProdutoLanche(combo.getLanche());
        return existeEstoqueBebida && existeEstoqueLanche;
    }

    private boolean validarExisteEstoqueProdutoLanche(Lanche lanche) {
        for (IProdutoComposicao produto : lanche.getIngredientesLanche()) {
            if (!validarExisteEstoqueProduto(produto.getCodigo()))
                return false;
        }
        return true;
    }

    private boolean validarExisteEstoqueProduto(UUID codigo) {
        Produto produtoExample = new Produto();
        produtoExample.setCodigo(codigo);

        Example<Produto> example = Example.of(produtoExample,
                ExampleMatcher.matching().withIgnoreCase().withIgnorePaths("id").withIgnorePaths("dataCadastro")
                        .withIgnorePaths("ativo").withIgnoreNullValues().withStringMatcher(StringMatcher.CONTAINING));

        List<Produto> lista = this._produtoRepository.findAll(example);

        if (lista.isEmpty())
            throw new RegraNegocioException("Código de produto inexistente");

        long id = lista.get(0).getId();
        double estoqueAtual = this._produtoRepository.countEstoqueById(id);

        return (estoqueAtual > 0);
    }

    public Page<CardapioListDto> listDto(int page) {
        return this.list(page).map(filterCardapio()).map(Mapper.pageMap(CardapioListDto.class));
    }

    public Page<CardapioListDto> listDtoFull(int page) {
        return this.list(page).map(Mapper.pageMap(CardapioListDto.class));
    }

    public Page<CardapioListDto> listActiveDto(int page) {
        return _repository.listActive(PageRequest.of((page - 1), 10)).map(filterCardapio())
                .map(Mapper.pageMap(CardapioListDto.class));
    }

    public Page<CardapioListDto> listDesactiveDto(int page) {
        return _repository.listDesactive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(CardapioListDto.class));
    }

    public Cardapio cardapioActive() {
        Page<CardapioListDto> page = this.listActiveDto(1);
        return this.find(page.getContent().get(0).id);
    }
}