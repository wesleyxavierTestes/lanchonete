package com.lanchonete.utils;

public final class MessageError {

    public static final String IS_MANDATORY = "obrigatório";
    public static final String MAX_LIMITE = "acima do tamanho máximo";
    public static final String MIN_LIMITE = "abaixo do tamanho mínimo";
    public static final String IS_INVALID = "invalido";

    public static final String NOT_EXISTS = "item inexistente";
    public static final String EXISTS = "item já existente";

    public static final String ERROS_DATABASE = "Erro ao salvar no banco de dados";
    public static final String PRODUTO_EXISTS = "Produto Inexistente";
	public static final Object PRODUTO_STOCK_EMPTY = "Produto sem estoque";

	private MessageError() {
    }
}