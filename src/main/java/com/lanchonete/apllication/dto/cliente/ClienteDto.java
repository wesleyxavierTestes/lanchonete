package com.lanchonete.apllication.dto.cliente;

import com.lanchonete.domain.enuns.cliente.EnumTipoPessoa;

import com.lanchonete.domain.enuns.cliente.EnumTipoCliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto  {

    public long id;
    public String nome;
    public String cpf;
    public String cnjp;

    public EnumTipoPessoa tipoPessoa;
    public EnumTipoCliente tipoCliente;
    public EnderecoDto endereco;
}
