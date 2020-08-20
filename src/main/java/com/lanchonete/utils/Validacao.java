package com.lanchonete.utils;

import org.springframework.web.bind.annotation.PathVariable;

public final class Validacao {
    private Validacao() {}

    public static boolean validarCep(@PathVariable(name = "cep") String cep) {
        boolean estaNull = cep != null;
        boolean tamanhoOk = cep.length() == 8;
        // boolean regexOk = cep.matches("[0-9]{8}]");
        boolean valid = estaNull && tamanhoOk; // && regexOk;
        return valid;
    }
}
