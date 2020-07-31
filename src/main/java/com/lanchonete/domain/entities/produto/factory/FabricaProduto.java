package com.lanchonete.domain.entities.produto.factory;

import java.util.Objects;

import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.bebida.Bebida;
import com.lanchonete.domain.entities.combo.Combo;
import com.lanchonete.domain.entities.lanche.Lanche;
import com.lanchonete.domain.entities.outros.Outros;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.entities.produto.baseentity.IProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoPedido;
import com.lanchonete.domain.entities.produto.processadores.BebidaProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.ComboProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.LancheProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.OutrosProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.ProdutoProcessaProduto;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
import com.lanchonete.infra.repositorys.produto.IAbstractProdutoRepository;
import com.lanchonete.infra.repositorys.produto.IAbstractProdutoRepository;

public final class FabricaProduto {
    public static IProduto GerarProdutoPorTipo(final EnumTipoProduto tipoProduto, Object produto) {

        if (EnumTipoProduto.Bebida == tipoProduto) {
            return Mapper.map(produto, Bebida.class).setTipoProduto(tipoProduto);
        }

        if (EnumTipoProduto.Combo == tipoProduto) {
            return Mapper.map(produto, Combo.class).setTipoProduto(tipoProduto);
        }

        if (EnumTipoProduto.Lanche == tipoProduto) {
            return Mapper.map(produto, Lanche.class).setTipoProduto(tipoProduto);
        }

        if (EnumTipoProduto.Outros == tipoProduto) {
            return Mapper.map(produto, Outros.class).setTipoProduto(tipoProduto);
        }

        if (EnumTipoProduto.Produto == tipoProduto) {
            return Mapper.map(produto, Outros.class).setTipoProduto(EnumTipoProduto.Outros);
        }

        if (EnumTipoProduto.Ingrediente == tipoProduto) {
            return Mapper.map(produto, Outros.class).setTipoProduto(EnumTipoProduto.Outros);
        }

        return null;
    }

    public static boolean validarProduto(IProduto produto, IAbstractProdutoRepository _produtoRepository) {
        boolean valido = false;
        try {
            if (produto instanceof Lanche) {
                valido = new LancheProcessaProduto(_produtoRepository).validarExisteEstoqueProduto((Lanche) produto);
            } else if (produto instanceof Combo) {
                valido = new ComboProcessaProduto(_produtoRepository).validarExisteEstoqueProduto((Combo) produto);
            } else if (produto instanceof Outros) {
                valido = new OutrosProcessaProduto(_produtoRepository).validarExisteEstoqueProduto((Outros) produto);
            } else if (produto instanceof Bebida) {
                valido = new BebidaProcessaProduto(_produtoRepository).validarExisteEstoqueProduto((Bebida) produto);
            } else if (produto instanceof Produto) {
                valido = new ProdutoProcessaProduto(_produtoRepository).validarExisteEstoqueProduto((Produto) produto);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return valido;
    }

    public static IProduto saveProduto(IProduto produto, IAbstractProdutoRepository _produtoRepository) {
        try {
            if (produto instanceof Lanche) {
                return new LancheProcessaProduto(_produtoRepository).save(produto);
            } else if (produto instanceof Combo) {
                return new ComboProcessaProduto(_produtoRepository).save(produto);
            } else if (produto instanceof Outros) {
                return new OutrosProcessaProduto(_produtoRepository).save(produto);
            } else if (produto instanceof Bebida) {
                return new BebidaProcessaProduto(_produtoRepository).save(produto);
            } else if (produto instanceof Produto) {
                return new ProdutoProcessaProduto(_produtoRepository).save(produto);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        String nome = Objects.nonNull(produto) ? produto.getNome() : "";
        throw new RegraNegocioException("Produto " + nome + " inválido");
    }

    private FabricaProduto() {

    }

    public static IProdutoPedido configurarPedidoItens(IProdutoPedido produto, IProdutoCardapio produtoCardapio) {
        try {
            produto.setValor(produtoCardapio.getValor());
            (produto).setCategoria((produtoCardapio).getCategoria());
            produto.setNome(produtoCardapio.getNome());

            if (produto instanceof Lanche) {
                ((Lanche) produto).setIngredientesLanche(((Lanche) produtoCardapio).getIngredientesLanche());
                ((Lanche) produto).calcularValor();
            } else if (produto instanceof Combo) {
                ((Combo) produto).setBebida(((Combo) produtoCardapio).getBebida());
                ((Combo) produto).setLanche(((Combo) produtoCardapio).getLanche());
                ((Combo) produto).calcularValor();
            }

        } catch (Exception e) {
            System.out.println(e);
            throw new RegraNegocioException("Item de Pedido inválido");
        }

        return produto;

    }
}