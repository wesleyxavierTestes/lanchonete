package com.lanchonete.domain.services.pedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lanchonete.apllication.dto.pedido.PedidoListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoPedido;
import com.lanchonete.domain.entities.produto.factory.FabricaProduto;
import com.lanchonete.domain.entities.produto.processadores.BebidaProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.ComboProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.LancheProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.OutrosProcessaProduto;
import com.lanchonete.domain.entities.venda.Venda;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.combo.IComboRepository;
import com.lanchonete.infra.repositorys.lanche.ILancheRepository;
import com.lanchonete.infra.repositorys.pedido.IPedidoRepository;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PedidoService extends BaseService<Pedido, IPedidoRepository> {

    @Autowired
    private IProdutoRepository _produtoRepository;
    
    @Autowired
    private IComboRepository _comboRepository;

    @Autowired
    private ILancheRepository _lancheRepository;

    @Autowired
    public PedidoService(IPedidoRepository repository) {
        super(repository);
    }

    public Page<PedidoListDto> listFilterDto(Pedido entity, int page) {
        return super.listFilter(entity, page).map(Mapper.pageMap(PedidoListDto.class));
    }

    public Page<PedidoListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(PedidoListDto.class));
    }

    public Page<PedidoListDto> listCancelDto(int page) {
        return _repository.listCancel(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(PedidoListDto.class));
    }

    public Page<PedidoListDto> listActive(int page) {
        return _repository.listActive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(PedidoListDto.class));
    }

    public Page<PedidoListDto> listDay(int page) {
        LocalDateTime data = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        return _repository.listDay(data.toString(), PageRequest.of((page - 1), 10))
                .map(Mapper.pageMap(PedidoListDto.class));
    }

    public Page<PedidoListDto> listClient(long id, int page) {
        return _repository.listClient(id, PageRequest.of((page - 1), 10)).map(Mapper.pageMap(PedidoListDto.class));
    }

    public void configurarPedido(Pedido entity, Cardapio cardapio, Cliente cliente) {
        entity.setValor(BigDecimal.ZERO);
        configurarPedidoItens(entity, cardapio);
        entity.calcularValorTotal();
        entity.setCodigo(UUID.randomUUID());
        entity.setCliente(cliente);
        entity = (Pedido)entity.fazerPedido();
    }

    private void configurarPedidoItens(Pedido entity, Cardapio cardapio) {
        try {
            List<IProdutoPedido> produtos = new ArrayList<>();
            for (IProdutoPedido produtoPedido : entity.getPedidoitens()) {
                for (IProdutoCardapio produtoCardapio : cardapio.getItensDisponiveis()) {
                    if (produtoPedido.getCodigo().equals(produtoCardapio.getCodigo())) {
                        IProdutoPedido produto = null;
                        
                        produto = processarProdutoPedido(produtoPedido);
                        
                        IProdutoPedido produtoMapeado = (IProdutoPedido) FabricaProduto.GerarProdutoPorTipo(produto.getTipoProduto(), produto);
                        produtoMapeado = (IProdutoPedido) FabricaProduto.configurarPedidoItens(produtoMapeado, produtoCardapio);
                        entity.setValor(entity.getValor().add(produtoMapeado.getValor()));
                        
                        produtos.add(produtoMapeado);
                        break;
                    }
                }
            }
            entity.setPedidoitens(produtos);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private IProdutoPedido processarProdutoPedido(IProdutoPedido produtoPedido) {
        IProdutoPedido produto;
        if (produtoPedido.getTipoProduto() == EnumTipoProduto.Bebida) {
            produto = (IProdutoPedido)new BebidaProcessaProduto(this._produtoRepository)
            .processar(produtoPedido);
        } else if (produtoPedido.getTipoProduto() == EnumTipoProduto.Lanche) {
            produto = (IProdutoPedido)new LancheProcessaProduto(this._produtoRepository)
            .processar(this._lancheRepository, produtoPedido);
        } else if (produtoPedido.getTipoProduto() == EnumTipoProduto.Combo) {
            produto = (IProdutoPedido)new ComboProcessaProduto(this._produtoRepository)
            .processar(this._comboRepository, produtoPedido);
        } else {
            produto = (IProdutoPedido)new OutrosProcessaProduto(this._produtoRepository)
            .processar(produtoPedido);
        }
        return produto;
    }

	public void adicionar(Pedido entity, IProdutoPedido produtoPedido) {
        IProdutoPedido produto = processarProdutoPedido(produtoPedido);
        List<IProdutoPedido> produtos = entity.getPedidoitens();

        produtos.add(produto);
        entity.setPedidoitens(produtos);
    }

	public void remove(Pedido entity, long itemId) {
        List<IProdutoPedido> produtos = entity.getPedidoitens();
        
        Optional<IProdutoPedido> produto = produtos.stream()
        .filter(item -> item.getId() == itemId).findFirst();

        produtos.remove(produto.get());

        entity.setPedidoitens(produtos);
	}

	public Venda finalizarPedido(Pedido entity) {
        entity.finalizarPedido().configurar();

        Venda venda = Venda.builder()
                .cliente(entity.getCliente())
                .valor(entity.getValorTotal())
                .valorDesconto(BigDecimal.ZERO)
                .valorTotal(entity.getValorTotal())
                .pedido(entity)
                .build();

		return  venda;
	}
}