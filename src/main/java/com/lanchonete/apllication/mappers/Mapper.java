package com.lanchonete.apllication.mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import com.lanchonete.apllication.dto.cardapio.CardapioDto;
import com.lanchonete.apllication.dto.cardapio.CardapioItemDto;
import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.cliente.ClienteDto;
import com.lanchonete.apllication.dto.cliente.EnderecoDto;
import com.lanchonete.apllication.dto.combo.ComboDto;
import com.lanchonete.apllication.dto.estoque.EstoqueDto;
import com.lanchonete.apllication.dto.lanche.IngredienteDto;
import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.apllication.dto.pedido.PedidoDto;
import com.lanchonete.apllication.dto.pedido.PedidoItemDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.dto.venda.VendaDto;
import com.lanchonete.apllication.dto.venda.VendaItemDto;
import com.lanchonete.apllication.dto.venda.VendaPedidoDto;
import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.entities.combo.Combo;
import com.lanchonete.domain.entities.ingrediente.Ingrediente;
import com.lanchonete.domain.entities.lanche.Lanche;
import com.lanchonete.domain.entities.outros.Outros;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.cliente.Endereco;
import com.lanchonete.domain.entities.estoque.AbstractEstoque;
import com.lanchonete.domain.entities.estoque.EstoqueEntrada;
import com.lanchonete.domain.entities.estoque.EstoqueSaida;
import com.lanchonete.domain.entities.estoque.IEstoque;
import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.domain.entities.pedido.PedidoAguardando;
import com.lanchonete.domain.entities.pedido.PedidoFinalizado;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoComposicao;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoPedido;
import com.lanchonete.domain.entities.produto.factory.FabricaProduto;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.entities.venda.Venda;
import com.lanchonete.domain.entities.venda.VendaItem;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
import com.lanchonete.utils.ObjectMapperUtils;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;

public final class Mapper {

    private Mapper() {
        super();
    }

    public static <T, Y> Function<T, Y> pageMap(final Class<Y> ref) {
        final Function<T, Y> converter = new Function<T, Y>() {
            @Override
            public Y apply(final T entity) {
                return Mapper.map(entity, ref);
            }
        };
        return converter;
    }

    public static <Y, T> T mapByJson(final Y o, final Class<T> ref) {
        try {
            final String json = ObjectMapperUtils.toJson(o);

            final T entity = ObjectMapperUtils.jsonTo(json, ref);

            return entity;
        } catch (final Exception e) {
            return null;
        }
    }

    public static <Y, T> T map(final Y o, final Class<T> ref) {
        try {
            final ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE);
            return mapper.map(o, ref);
        } catch (final Exception e) {
            return null;
        }
    }
    
    public static IProdutoCardapio map(final CardapioItemDto entityDto, EnumTipoProduto tipoProduto) {
        IProdutoCardapio map = Mapper.map(entityDto, Outros.class);
        map.setCodigo(UUID.fromString(entityDto.codigo));
        map.setTipoProduto(tipoProduto);
        return map;
    }

    public static Categoria map(final CategoriaDto entityDto, final Categoria entity) {
        return CategoriaMapper.update(entityDto, entity);
    }

    public static Categoria map(final CategoriaDto entity) {
        return Mapper.map(entity, Categoria.class);
    }

    public static CategoriaDto map(final Categoria entity) {
        return Mapper.map(entity, CategoriaDto.class);
    }

    public static Cardapio map(final CardapioDto entityDto, final Cardapio entity) {
        return CardapioMapper.update(entityDto, entity);
    }

    public static Cardapio map(final CardapioDto entity) {
        final List<IProdutoCardapio> itensDisponiveis = new ArrayList<>();
        List<CardapioItemDto> copy = null;

        if (Objects.nonNull(entity.itensDisponiveis)) {
            entity.itensDisponiveis.stream().forEach(item -> {
                IProdutoCardapio gerarProdutoPorTipo = (IProdutoCardapio) FabricaProduto.GerarProdutoPorTipo(item.tipoProduto, item);
                itensDisponiveis.add(gerarProdutoPorTipo);
                });

            copy = new ArrayList<>(entity.itensDisponiveis);
            entity.itensDisponiveis = null;
        }

        final Cardapio lancheMap = Mapper.map(entity, Cardapio.class);
        if (Objects.nonNull(lancheMap))
            lancheMap.setItensDisponiveis(itensDisponiveis);

        entity.itensDisponiveis = copy;

        return lancheMap;
    }

    public static CardapioDto map(final Cardapio entity) {
        return Mapper.map(entity, CardapioDto.class);
    }

    public static Cliente map(final ClienteDto entityDto, final Cliente entity) {
        return ClienteMapper.update(entityDto, entity);
    }

    public static Cliente map(final ClienteDto entity) {
        return Mapper.map(entity, Cliente.class);
    }

    public static ClienteDto map(final Cliente entity) {
        return Mapper.map(entity, ClienteDto.class);
    }

    public static Venda map(final VendaDto entityDto, final Venda entity) {
        return VendaMapper.update(entityDto, entity);
    }

    public static Venda map(final VendaDto entity) {
        final List<VendaItem> vendaItens = new ArrayList<>();
        List<VendaItemDto> copy = null;

        if (Objects.nonNull(entity.vendaItens)) {
            entity.vendaItens.forEach(item -> {

                VendaItem vendaItem = new VendaItem();
                vendaItem.setId(item.id);
                vendaItem.setTipoProduto(EnumTipoProduto.Venda);
                vendaItem.setValor((item.valor));
                vendaItem.setValorDesconto((item.valorDesconto));
                vendaItem.setValorTotal((item.valorTotal));
                Pedido pedidoMap = Mapper.map(item.pedido);
                vendaItem.setPedido(pedidoMap);

                vendaItens.add(vendaItem);
            });

            copy = new ArrayList<>(entity.vendaItens);
            entity.vendaItens = null;
        }

        final Venda vendaMap = Mapper.map(entity, Venda.class);
        if (Objects.nonNull(vendaMap))
            vendaMap.setVendaItens(vendaItens);

        entity.vendaItens = copy;

        return vendaMap;

    }

    public static VendaDto map(final Venda entity) {
        return Mapper.map(entity, VendaDto.class);
    }

    public static Combo map(final ComboDto entityDto, final Combo entity) {
        return ComboMapper.update(entityDto, entity);
    }

    public static Combo map(final ComboDto entity) {
        return Mapper.map(entity, Combo.class);
    }

    public static ComboDto map(final Combo entity) {
        return Mapper.map(entity, ComboDto.class);
    }

    public static Endereco map(final EnderecoDto entityDto, final Endereco entity) {
        return EnderecoMapper.update(entityDto, entity);
    }

    public static Endereco map(final EnderecoDto entity) {
        return Mapper.map(entity, Endereco.class);
    }

    public static EnderecoDto map(final Endereco entity) {
        return Mapper.map(entity, EnderecoDto.class);
    }

    public static EstoqueEntrada map(final EstoqueDto entityDto, final EstoqueEntrada entity) {
        return EstoqueMapper.update(entityDto, entity);
    }

    public static <T extends IEstoque> T map(final EstoqueDto entity, final boolean i) {

        return (T) ((i) ? Mapper.map(entity, EstoqueEntrada.class) : Mapper.map(entity, EstoqueSaida.class));
    }

    public static EstoqueDto map(final EstoqueEntrada entity) {
        return Mapper.map(entity, EstoqueDto.class);
    }

    public static EstoqueSaida map(final EstoqueDto entityDto, final EstoqueSaida entity) {
        return EstoqueMapper.update(entityDto, entity);
    }

    public static EstoqueDto map(final AbstractEstoque entity) {
        return Mapper.map(entity, EstoqueDto.class);
    }

    public static EstoqueDto map(final EstoqueSaida entity) {
        return Mapper.map(entity, EstoqueDto.class);
    }

    public static Lanche map(final LancheDto entityDto, final Lanche entity) {
        return LancheMapper.update(entityDto, entity);
    }

    public static Lanche map(final LancheDto entity) {
        final List<IProdutoComposicao> ingredientes = new ArrayList<>();
        List<IngredienteDto> copy = null;

        if (Objects.nonNull(entity.ingredientesLanche)) {
            entity.ingredientesLanche.stream().forEach(item -> ingredientes.add(Mapper.map(item, Ingrediente.class)));

            copy = new ArrayList<>(entity.ingredientesLanche);
            entity.ingredientesLanche = null;
        }

        final Lanche lancheMap = Mapper.map(entity, Lanche.class);
        if (Objects.nonNull(lancheMap))
            lancheMap.setIngredientesLanche(ingredientes);

        entity.ingredientesLanche = copy;

        return lancheMap;
    }

    public static LancheDto map(final Lanche entity) {
        return Mapper.map(entity, LancheDto.class);
    }

    public static Pedido map(final PedidoDto entityDto, final Pedido entity) {
        return PedidoMapper.update(entityDto, entity);
    }

    public static PedidoAguardando map(final PedidoDto entity) {
        final List<IProdutoPedido> pedidoitens = new ArrayList<>();
        List<PedidoItemDto> copy = null;

        if (Objects.nonNull(entity.pedidoitens)) {
            entity.pedidoitens.stream().forEach(item -> {
                Outros itemnew = new Outros();
                itemnew.setId(item.id);
                itemnew.setTipoProduto(item.tipoProduto);
                itemnew.setCodigo(UUID.fromString(item.codigo));
                pedidoitens.add(itemnew);
            });

            copy = new ArrayList<>(entity.pedidoitens);
            entity.pedidoitens = null;
        }

        final PedidoAguardando pedidoMap = Mapper.map(entity, PedidoAguardando.class);
        if (Objects.nonNull(pedidoMap))
            pedidoMap.setPedidoitens(pedidoitens);

        entity.pedidoitens = copy;

        return pedidoMap;
    }

    public static PedidoFinalizado map(final VendaPedidoDto entity) {

        PedidoFinalizado pedido = new PedidoFinalizado();
        pedido.setId(entity.id);
        pedido.setCodigo(UUID.fromString(entity.codigo));

        return pedido;
    }

    public static PedidoDto map(final Pedido entity) {
        return Mapper.map(entity, PedidoDto.class);
    }

    public static Produto map(final ProdutoDto entityDto, final Produto entity) {
        return ProdutoMapper.update(entityDto, entity);
    }

    public static Produto map(final ProdutoDto entity) {
        return Mapper.map(entity, Produto.class);
    }

    public static ProdutoDto map(final Produto entity) {
        return Mapper.map(entity, ProdutoDto.class);
    }

    public static Ingrediente map(final IngredienteDto entity) {
        return Mapper.map(entity, Ingrediente.class);
    }

    public static IngredienteDto map(final Ingrediente entity) {
        return Mapper.map(entity, IngredienteDto.class);
    }
}