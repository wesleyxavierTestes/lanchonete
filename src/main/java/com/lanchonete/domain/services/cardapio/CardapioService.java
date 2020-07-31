package com.lanchonete.domain.services.cardapio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.lanchonete.apllication.dto.cardapio.CardapioListDto;
import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.bebida.Bebida;
import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.entities.combo.Combo;
import com.lanchonete.domain.entities.lanche.Lanche;
import com.lanchonete.domain.entities.outros.Outros;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.factory.FabricaProduto;
import com.lanchonete.domain.entities.produto.processadores.BebidaProcessaProduto;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.cardapio.ICardapioRepository;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
            Set<IProdutoCardapio> getItensDisponiveis = filterProduto(cardapio);
            cardapio.setItensDisponiveis(getItensDisponiveis);
            return cardapio;
        };
    }

    private Set<IProdutoCardapio> filterProduto(Cardapio cardapio) {
        Set<IProdutoCardapio> getItensDisponiveis = new HashSet<>();
        List<IProdutoCardapio> itensDisponiveis = new ArrayList<>(cardapio.getItensDisponiveis());

        for (IProdutoCardapio produtoCardapio : itensDisponiveis) {
            try {
                boolean valido = FabricaProduto.validarProduto(produtoCardapio, this._produtoRepository);
                
                if (valido) {
                    getItensDisponiveis.add(produtoCardapio);
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return getItensDisponiveis;
    }


    public Page<CardapioListDto> listDto(int page) {
        return this.list(page)
        .map(filterCardapio())
        .map(Mapper.pageMap(CardapioListDto.class));
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
        try {
            return this.find(page.getContent().get(0).id);
        } catch (Exception e) {
            throw new RegraNegocioException("Cardapio ausente");
        }
    }

    public void criarCardapio(Cardapio entity) {
        for (IProdutoCardapio produtoCardapio : entity.getItensDisponiveis()) {
            if (produtoCardapio instanceof Bebida) {
                new BebidaProcessaProduto(this._produtoRepository).processar(produtoCardapio);
            } else if (produtoCardapio instanceof Lanche) {
                new BebidaProcessaProduto(this._produtoRepository).processar(produtoCardapio);
            } else if (produtoCardapio instanceof Combo) {
                new BebidaProcessaProduto(this._produtoRepository).processar(produtoCardapio);
            } else if (produtoCardapio instanceof Outros) {
                new BebidaProcessaProduto(this._produtoRepository).processar(produtoCardapio);
            }
        }
    }
}