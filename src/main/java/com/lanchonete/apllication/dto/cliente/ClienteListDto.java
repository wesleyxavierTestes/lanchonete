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

    public String nome;

    @JsonIgnore
    public String cpf;
    @JsonIgnore
    public String cnjp;

    @JsonGetter("nome")
    public String getCpfCnpj() {
        return tipoPessoa == EnumTipoPessoa.Fisica ? this.cpf : this.cnjp;
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
