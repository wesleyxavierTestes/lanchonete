package com.lanchonete.domain.entities.produto.factory;

import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.bebida.Bebida;
import com.lanchonete.domain.entities.combo.Combo;
import com.lanchonete.domain.entities.lanche.Lanche;
import com.lanchonete.domain.entities.outros.Outros;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.entities.produto.baseentity.IProduto;
import com.lanchonete.domain.entities.produto.processadores.BebidaProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.ComboProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.LancheProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.OutrosProcessaProduto;
import com.lanchonete.domain.entities.produto.processadores.ProdutoProcessaProduto;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;

public final class FabricaProduto {
    public static IProduto GerarProdutoPorTipo(
        final EnumTipoProduto tipoProduto, Object produto) {

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

    public static boolean validarProduto(IProduto produto, IProdutoRepository _produtoRepository) {
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
    
    
    private FabricaProduto() {

    }
}