package com.lanchonete.apllication.dto.cliente;

import com.lanchonete.domain.enuns.cliente.EnumTipoPessoa;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.lanchonete.domain.enuns.cliente.EnumTipoCliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto  {

    public long id;
    @NotNull(message = "Name is mandatory")
    @Max(message = "Name is mandatory", value = 200)
    public String nome;
    
    @Max(message = "Name is mandatory", value = 8)
    public String cpf;

    @Max(message = "Name is mandatory", value = 14)
    public String cnjp;

    @NotNull(message = "Name is mandatory")
    public EnumTipoPessoa tipoPessoa;

    @NotNull(message = "Name is mandatory")
    public EnumTipoCliente tipoCliente;

    @NotNull
    public EnderecoDto endereco = new EnderecoDto();
}
