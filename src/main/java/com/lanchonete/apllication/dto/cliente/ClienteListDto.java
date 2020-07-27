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

    @JsonIgnore
    public EnumTipoPessoa tipoPessoa;

    @JsonIgnore
    public EnumTipoCliente tipoCliente;

    @JsonGetter("tipoCliente")
    public String geTtipoCliente() {
        return tipoCliente == EnumTipoCliente.GeraFisco ? "Comum" : "Final";
    }
}
