package com.lanchonete.utils;

public final class UrlConstants {

    private UrlConstants() { }

    public final static String getViaCep(String cep) {
        return String.format("https://viacep.com.br/ws/$s/json/", cep);
    }
}