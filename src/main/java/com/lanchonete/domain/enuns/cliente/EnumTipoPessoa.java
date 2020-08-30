package com.lanchonete.domain.enuns.cliente;

public enum EnumTipoPessoa {
    Fisica("Fisica", "Cpf"),
    Juridica("Juridica", "Cnpj");

    public final String label;
    public final String tipo;

    private EnumTipoPessoa(String label,  String tipo) {
        this.label = label;
        this.tipo = tipo;
    }
}