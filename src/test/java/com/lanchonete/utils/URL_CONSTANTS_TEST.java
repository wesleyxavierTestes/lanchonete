package com.lanchonete.utils;

public final class URL_CONSTANTS_TEST {

    private URL_CONSTANTS_TEST() {
    }

    public final static String ClienteList = "http://localhost:%s/api/cliente/list";
    public final static String ClienteFind = "http://localhost:%s/api/cliente/find";
    public final static String ClienteSave = "http://localhost:%s/api/cliente/save";
    public final static String ClienteSaveDefault = "http://localhost:%s/api/cliente/save/default";
    public final static String ClienteUpdate = "http://localhost:%s/api/cliente/update";
    public final static String ClienteDelete = "http://localhost:%s/api/cliente/delete";

    public final static String CategoriaList = "http://localhost:%s/api/categoria/list";
    public final static String CategoriaFind = "http://localhost:%s/api/categoria/find";
    public final static String CategoriaSave = "http://localhost:%s/api/categoria/save";
    public final static String CategoriaUpdate = "http://localhost:%s/api/categoria/update";
    public final static String CategoriaDelete = "http://localhost:%s/api/categoria/delete";
    
    public final static String EnderecoList = "http://localhost:%s/api/endereco/list";
    public final static String EnderecoFind = "http://localhost:%s/api/endereco/find";
    public final static String EnderecoSave = "http://localhost:%s/api/endereco/save";
    public final static String EnderecoUpdate = "http://localhost:%s/api/endereco/update";
    public final static String EnderecoDelete = "http://localhost:%s/api/endereco/delete";

    public final static String ProdutoList = "http://localhost:%s/api/produto/list";
    public final static String ProdutoFind = "http://localhost:%s/api/produto/find";
    public final static String ProdutoSave = "http://localhost:%s/api/produto/save";
    public final static String ProdutoUpdate = "http://localhost:%s/api/produto/update";
    public final static String ProdutoDelete = "http://localhost:%s/api/produto/delete";
}
