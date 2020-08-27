package com.lanchonete.domain.services.cardapio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lanchonete.apllication.dto.cardapio.CardapioListDto;
import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.processadores.BebidaProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.ComboProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.LancheProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.OutrosProcessaProduto;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.bebida.IBebidaRepository;
import com.lanchonete.infra.repositorys.cardapio.ICardapioRepository;
import com.lanchonete.infra.repositorys.combo.IComboRepository;
import com.lanchonete.infra.repositorys.lanche.ILancheRepository;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class CardapioService extends BaseService<Cardapio, ICardapioRepository> {

    @Autowired
    private IProdutoRepository _produtoRepository;

    @Autowired
    private IComboRepository _comboRepository;

    @Autowired
    private ILancheRepository _lancheRepository;

    @Autowired
    private IBebidaRepository _bebidaRepository;

    @Autowired
    public CardapioService(final ICardapioRepository repository) {
        super(repository);
    }

    public Page<CardapioListDto> listFilterDto(final Cardapio entity, final int page) {
        return super.listFilter(entity, page).map(Mapper.pageMap(CardapioListDto.class));
    }

    public Page<CardapioListDto> listDto(final int page) {
        return this.list(page).map(Mapper.pageMap(CardapioListDto.class));
    }

    public Page<CardapioListDto> listDtoFull(final int page) {
        return this.list(page).map(Mapper.pageMap(CardapioListDto.class));
    }

    public Page<CardapioListDto> listActiveDto(final int page) {
        return _repository.listActive(PageRequest.of((page - 1), 10))
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
            if (produtoCardapio.getTipoProduto() == EnumTipoProduto.Bebida) {
                return (IProdutoCardapio) new BebidaProcessaProduto(this._produtoRepository)
                .processar(this._bebidaRepository, produtoCardapio);
            } else if (produtoCardapio.getTipoProduto() == EnumTipoProduto.Lanche) {
                return (IProdutoCardapio) new LancheProcessaProduto(this._produtoRepository)
                .processar(produtoCardapio);
            } else if (produtoCardapio.getTipoProduto() == EnumTipoProduto.Combo) {
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
        final Optional<IProdutoCardapio> exist = itensDisponiveis.stream()
        .filter(c -> produtoCardapioId == c.getId())
                .findFirst();

        if (exist.isPresent()) {
            throw new RegraNegocioException("Item de Cardapio existente");
        }
    }

    private IProdutoCardapio processaProdutoCardapioPorTipo(IProdutoCardapio produtoCardapio) {
        if (produtoCardapio.getTipoProduto() == EnumTipoProduto.Bebida) {
            produtoCardapio = (IProdutoCardapio) new BebidaProcessaProduto(this._produtoRepository)
            .processar(this._bebidaRepository, produtoCardapio);
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

	public void remove(Cardapio entity, long itemId) {

        List<IProdutoCardapio> produtos = entity.getItensDisponiveis();
        
        Optional<IProdutoCardapio> produto = produtos.stream()
        .filter(item -> item.getId() == itemId).findFirst();

        produtos.remove(produto.get());

        entity.setItensDisponiveis(produtos);
	}
}