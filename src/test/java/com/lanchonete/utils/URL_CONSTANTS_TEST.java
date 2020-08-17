package com.lanchonete.utils;

import java.net.MalformedURLException;

import java.net.URL;
import org.springframework.web.client.RestClientException;

public final class URL_CONSTANTS_TEST {

    private URL_CONSTANTS_TEST() {
    }

    public static String getUrl(String url, int port) {
        String urlnew = "";
        try {
            urlnew = new URL(String.format(url, port)).toString();
        } catch (RestClientException | MalformedURLException e) {
            e.printStackTrace();
        }
        return urlnew;
    }

    public final static String ClienteList = "http://localhost:%s/api/cliente/list";
    public final static String ClienteFind = "http://localhost:%s/api/cliente/find";
    public final static String ClienteSave = "http://localhost:%s/api/cliente/save";
    public final static String ClienteSaveDefault = "http://localhost:%s/api/cliente/save/default";
    public final static String ClienteUpdate = "http://localhost:%s/api/cliente/update";
    public final static String ClienteDelete = "http://localhost:%s/api/cliente/delete";
    public final static String ClienteActive = "http://localhost:%s/api/cliente/active";
    public final static String ClienteDesactive = "http://localhost:%s/api/cliente/desactive";

    public final static String CategoriaList = "http://localhost:%s/api/categoria/list";
    public final static String CategoriaFind = "http://localhost:%s/api/categoria/find";
    public final static String CategoriaSave = "http://localhost:%s/api/categoria/save";
    public final static String CategoriaUpdate = "http://localhost:%s/api/categoria/update";
    public final static String CategoriaActive = "http://localhost:%s/api/categoria/active";
    public final static String CategoriaDesactive = "http://localhost:%s/api/categoria/desactive";

    public final static String PedidoList = "http://localhost:%s/api/pedido/list";
    public final static String PedidoFind = "http://localhost:%s/api/pedido/find";
    public final static String PedidoFindCancel = "http://localhost:%s/api/pedido/find/cancel";
    public final static String PedidoSave = "http://localhost:%s/api/pedido/save";
    public final static String PedidoUpdate = "http://localhost:%s/api/pedido/update";
    public final static String PedidoCancel = "http://localhost:%s/api/pedido/cancel";

    public final static String VendaList = "http://localhost:%s/api/venda/list";
    public final static String VendaFind = "http://localhost:%s/api/venda/find";
    public final static String VendaFindCancel = "http://localhost:%s/api/venda/find/cancel";
    public final static String VendaSave = "http://localhost:%s/api/venda/save";
    public final static String VendaCancel = "http://localhost:%s/api/venda/cancel";

    public final static String LancheList = "http://localhost:%s/api/lanche/list";
    public final static String LancheFind = "http://localhost:%s/api/lanche/find";
    public final static String LancheSave = "http://localhost:%s/api/lanche/save";
    public final static String LancheActive = "http://localhost:%s/api/lanche/active";
    public final static String LancheDesactive = "http://localhost:%s/api/lanche/desactive";
    
    public final static String ComboList = "http://localhost:%s/api/combo/list";
    public final static String ComboFind = "http://localhost:%s/api/combo/find";
    public final static String ComboSave = "http://localhost:%s/api/combo/save";
    public final static String ComboActive = "http://localhost:%s/api/combo/active";
    public final static String ComboDesactive = "http://localhost:%s/api/combo/desactive";
    
    public final static String CardapioList = "http://localhost:%s/api/cardapio/list";
    public final static String CardapioFind = "http://localhost:%s/api/cardapio/find";
    public final static String CardapioSave = "http://localhost:%s/api/cardapio/save";
    public final static String CardapioActive = "http://localhost:%s/api/cardapio/active";
    public final static String CardapioDesactive = "http://localhost:%s/api/cardapio/desactive";
    
    public final static String EnderecoList = "http://localhost:%s/api/endereco/list";
    public final static String EnderecoFind = "http://localhost:%s/api/endereco/find";
    public final static String EnderecoSave = "http://localhost:%s/api/endereco/save";
    public final static String EnderecoUpdate = "http://localhost:%s/api/endereco/update";
    public final static String EnderecoDelete = "http://localhost:%s/api/endereco/delete";

    public final static String ProdutoList = "http://localhost:%s/api/produto/list";
    public final static String ProdutoFind = "http://localhost:%s/api/produto/find";
    public final static String ProdutoSave = "http://localhost:%s/api/produto/save";
    public final static String ProdutoUpdate = "http://localhost:%s/api/produto/update";
    public final static String ProdutoActive = "http://localhost:%s/api/produto/active";
    public final static String ProdutoDesactive = "http://localhost:%s/api/produto/desactive";

    public final static String EstoqueList = "http://localhost:%s/api/estoque/list";
    public final static String EstoqueListEntrance = "http://localhost:%s/api/estoque/list/entrance";
    public final static String EstoqueListLeave = "http://localhost:%s/api/estoque/list/leave";
    public final static String EstoqueFind = "http://localhost:%s/api/estoque/find";
    public final static String EstoqueSaveAdd = "http://localhost:%s/api/estoque/save/add";
    public final static String EstoqueSaveRemove = "http://localhost:%s/api/estoque/save/remove";
    public final static String EstoqueDelete = "http://localhost:%s/api/estoque/delete";
}
