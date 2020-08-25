package com.lanchonete.domain.services.cardapio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.lanchonete.apllication.dto.cardapio.CardapioListDto;
import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.bebida.Bebida;
import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.entities.combo.Combo;
import com.lanchonete.domain.entities.lanche.Lanche;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.factory.FabricaProduto;
import com.lanchonete.domain.entities.produto.processadores.BebidaProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.ComboProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.LancheProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.OutrosProcessaProduto;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.cardapio.ICardapioRepository;
import com.lanchonete.infra.repositorys.combo.IComboRepository;
import com.lanchonete.infra.repositorys.lanche.ILancheRepository;
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
    private IComboRepository _comboRepository;

    @Autowired
    private ILancheRepository _lancheRepository;

    @Autowired
    public CardapioService(final ICardapioRepository repository) {
        super(repository);
        _repository = repository;
    }

    public Page<CardapioListDto> listFilterDto(final Cardapio entity, final int page) {
        return super.listFilter(entity, page).map(filterCardapio()).map(Mapper.pageMap(CardapioListDto.class));
    }

    private Function<? super Cardapio, ? extends Cardapio> filterCardapio() {
        return cardapio -> {
            final List<IProdutoCardapio> getItensDisponiveis = filterProduto(cardapio);
            cardapio.setItensDisponiveis(getItensDisponiveis);
            return cardapio;
        };
    }

    private List<IProdutoCardapio> filterProduto(final Cardapio cardapio) {
        final List<IProdutoCardapio> getItensDisponiveis = new ArrayList<>();
        final List<IProdutoCardapio> itensDisponiveis = new ArrayList<>(cardapio.getItensDisponiveis());

        for (final IProdutoCardapio produtoCardapio : itensDisponiveis) {
            try {
                final boolean valido = FabricaProduto.validarProduto(produtoCardapio, this._produtoRepository);

                if (valido) {
                    getItensDisponiveis.add(produtoCardapio);
                }

            } catch (final Exception e) {
                System.out.println(e);
            }
        }

        return getItensDisponiveis;
    }

    public Page<CardapioListDto> listDto(final int page) {
        return this.list(page).map(filterCardapio()).map(Mapper.pageMap(CardapioListDto.class));
    }

    public Page<CardapioListDto> listDtoFull(final int page) {
        return this.list(page).map(Mapper.pageMap(CardapioListDto.class));
    }

    public Page<CardapioListDto> listActiveDto(final int page) {
        return _repository.listActive(PageRequest.of((page - 1), 10)).map(filterCardapio())
                .map(Mapper.pageMap(CardapioListDto.class));
    }

    public Page<CardapioListDto> listDesactiveDto(final int page) {
        return _repository.listDesactive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(CardapioListDto.class));
    }

    public Cardapio cardapioActive() {
        final Page<CardapioListDto> page = this.listActiveDto(1);
        try {
            return this.find(page.getContent().get(0).id);
        } catch (final Exception e) {
            throw new RegraNegocioException("Cardapio ausente");
        }
    }

    public void criarCardapio(final Cardapio entity) {
        final List<IProdutoCardapio> itensDisponiveis = entity.getItensDisponiveis().stream().map(produtoCardapio -> {
            if (produtoCardapio instanceof Bebida) {
                return (IProdutoCardapio) new BebidaProcessaProduto(this._produtoRepository).processar(produtoCardapio);
            } else if (produtoCardapio instanceof Lanche) {
                return (IProdutoCardapio) new LancheProcessaProduto(this._produtoRepository).processar(produtoCardapio);
            } else if (produtoCardapio instanceof Combo) {
                return (IProdutoCardapio)new ComboProcessaProduto(this._produtoRepository)
                .processar(this._comboRepository, produtoCardapio);
            } else {
                return (IProdutoCardapio) new OutrosProcessaProduto(this._produtoRepository).processar(produtoCardapio);
            }

        }).collect(Collectors.toList());

        entity.setItensDisponiveis(itensDisponiveis);
    }

    public void criarCardapio(final Cardapio entity, IProdutoCardapio produtoCardapio) {
        final List<IProdutoCardapio> itensDisponiveis = entity.getItensDisponiveis();

        produtoCardapio = processaProdutoCardapioPorTipo(produtoCardapio);
        
        validaDuplicacaoItemCardapio(produtoCardapio, itensDisponiveis);

        itensDisponiveis.add(produtoCardapio);

        entity.setItensDisponiveis(itensDisponiveis);
    }

    private void validaDuplicacaoItemCardapio(IProdutoCardapio produtoCardapio, final List<IProdutoCardapio> itensDisponiveis) {
        final long produtoCardapioId = produtoCardapio.getId();
        final Optional<IProdutoCardapio> exist = itensDisponiveis.stream().filter(c -> produtoCardapioId == c.getId())
                .findFirst();

        if (exist.isPresent()) {
            throw new RegraNegocioException("Item de Cardapio existente");
        }
    }

    private IProdutoCardapio processaProdutoCardapioPorTipo(IProdutoCardapio produtoCardapio) {
        if (produtoCardapio.getTipoProduto() == EnumTipoProduto.Bebida) {
            produtoCardapio = (IProdutoCardapio) new BebidaProcessaProduto(this._produtoRepository)
                    .processar(produtoCardapio);
        } else if (produtoCardapio.getTipoProduto() == EnumTipoProduto.Lanche) {
            produtoCardapio = (IProdutoCardapio) new LancheProcessaProduto(this._produtoRepository)
                    .processar(this._lancheRepository, produtoCardapio);
        } else if (produtoCardapio.getTipoProduto() == EnumTipoProduto.Combo) {
            produtoCardapio = (IProdutoCardapio) new ComboProcessaProduto(this._produtoRepository)
                    .processar(this._comboRepository, produtoCardapio);
        } else {
            produtoCardapio = (IProdutoCardapio) new OutrosProcessaProduto(this._produtoRepository)
                    .processar(produtoCardapio);
        }
        return produtoCardapio;
    }
}