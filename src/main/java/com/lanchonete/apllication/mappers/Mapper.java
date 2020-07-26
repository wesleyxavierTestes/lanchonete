package com.lanchonete.apllication.mappers;

import java.util.function.Function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lanchonete.apllication.dto.cardapio.CardapioDto;
import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.cliente.ClienteDto;
import com.lanchonete.apllication.dto.cliente.EnderecoDto;
import com.lanchonete.apllication.dto.combo.ComboDto;
import com.lanchonete.apllication.dto.estoque.EstoqueDto;
import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.apllication.dto.pedido.PedidoDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.dto.venda.VendaDto;
import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.entities.cardapio.combo.Combo;
import com.lanchonete.domain.entities.cardapio.lanche.Lanche;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.cliente.Endereco;
import com.lanchonete.domain.entities.estoque.AbstractEstoque;
import com.lanchonete.domain.entities.estoque.EstoqueEntrada;
import com.lanchonete.domain.entities.estoque.EstoqueSaida;
import com.lanchonete.domain.entities.estoque.IEstoque;
import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.domain.entities.produto.entities.Produto;
import com.lanchonete.domain.entities.venda.Venda;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;

public final class Mapper {

    private Mapper() {
        super();
    }


    public static <T, Y> Function<T, Y> pageMap(Class<Y> ref) {
        Function<T, Y> converter = new Function<T, Y>() {
            @Override
            public Y apply(T entity) {
                return Mapper.map(entity, ref);
            }
        };
        return converter;
    }

    public static <Y, T> T map(final Y o, final Class<T> ref) {
        try {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE);
            return mapper.map(o, ref);
        } catch (Exception e) {
            return null;
        }
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
        return Mapper.map(entity, Cardapio.class);
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
        return Mapper.map(entity, Venda.class);
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
        return Mapper.map(entity, Lanche.class);
    }

    public static LancheDto map(final Lanche entity) {
        return Mapper.map(entity, LancheDto.class);
    }

    public static Pedido map(final PedidoDto entityDto, final Pedido entity) {
        return PedidoMapper.update(entityDto, entity);
    }

    public static Pedido map(final PedidoDto entity) {
        return Mapper.map(entity, Pedido.class);
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
}