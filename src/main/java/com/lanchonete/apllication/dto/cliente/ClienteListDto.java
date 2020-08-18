package com.lanchonete.apllication.dto.cliente;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lanchonete.domain.enuns.cliente.EnumTipoCliente;
import com.lanchonete.domain.enuns.cliente.EnumTipoPessoa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteListDto  {

    public long id;
    public boolean ativo;

    public String nome;

    @JsonIgnore
    public String cpf;

    @JsonIgnore
    public String cnjp;

    @JsonGetter("cpfCnpj")
    public String getCpfCnpj() {
        return tipoPessoa == EnumTipoPessoa.Fisica ? cpfFormat() : cnpjFormat();
    }

    private String cnpjFormat() {
        return this.cnjp;
    }

    private String cpfFormat() {
        return this.cpf;
    }

    public EnumTipoPessoa tipoPessoa;

    public EnumTipoCliente tipoCliente;
}
