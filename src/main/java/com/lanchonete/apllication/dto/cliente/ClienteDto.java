package com.lanchonete.apllication.dto.cliente;

import com.lanchonete.domain.enuns.cliente.EnumTipoPessoa;
import com.lanchonete.utils.MessageError;

import java.util.ArrayList;
import java.util.List;

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
    @NotNull(message = MessageError.IS_MANDATORY)
    @Max(message = MessageError.MAX_LIMITE, value = 2)
    public String nome;
    
    @Max(message = MessageError.MAX_LIMITE, value = 8)
    public String cpf;

    @Max(message = MessageError.IS_MANDATORY, value = 14)
    public String cnjp;

    @NotNull(message = MessageError.IS_MANDATORY)
    public EnumTipoPessoa tipoPessoa;

    @NotNull(message = MessageError.IS_MANDATORY)
    public EnumTipoCliente tipoCliente;

    @NotNull(message = MessageError.IS_MANDATORY)
    public EnderecoDto endereco = new EnderecoDto();

    public List<EnderecoDto> lista = new ArrayList<>();
}
