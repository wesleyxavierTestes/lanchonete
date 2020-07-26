package com.lanchonete.apllication.dto.cliente;

import com.lanchonete.domain.enuns.cliente.EnumTipoPessoa;
import com.lanchonete.utils.MessageError;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lanchonete.domain.enuns.cliente.EnumTipoCliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto  {

    public long id;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Size(max = 150, message = MessageError.MAX_LIMITE)
    public String nome;
    
    @Size(max = 11, min = 11, message = MessageError.MAX_LIMITE)
    public String cpf;

    @Size(max = 14, min = 14, message = MessageError.MAX_LIMITE)
    public String cnjp;

    @Size(max = 100, message = MessageError.MAX_LIMITE)
    private String email;

    @Size(max = 9, message = MessageError.MAX_LIMITE)
    private String rg;

    @NotNull(message = MessageError.IS_MANDATORY)
    public EnumTipoPessoa tipoPessoa;

    @NotNull(message = MessageError.IS_MANDATORY)
    public EnumTipoCliente tipoCliente;

    @NotNull(message = MessageError.IS_MANDATORY)
    public EnderecoDto endereco;
}
