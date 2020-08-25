package com.lanchonete.apllication.dto.cliente;

import com.lanchonete.domain.enuns.cliente.EnumTipoPessoa;
import com.lanchonete.utils.MessageError;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lanchonete.domain.enuns.cliente.EnumTipoCliente;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ClienteDto  {

    public long id;
    public boolean ativo;

    @NotNull(message = MessageError.IS_MANDATORY)
    @NotEmpty(message = MessageError.IS_MANDATORY)
    @Size(max = 150, message = MessageError.MAX_LIMITE)
    public String nome;
    
    @Size(max = 11, message = MessageError.MAX_LIMITE)
    @Size(min = 11, message = MessageError.MIN_LIMITE)
    public String cpf;

    @Size(max = 14, message = MessageError.MAX_LIMITE)
    @Size(min = 14, message = MessageError.MIN_LIMITE)
    public String cnpj;

    @Size(max = 100, message = MessageError.MAX_LIMITE)
    @Email(message = "Email " + MessageError.IS_INVALID)
    public String email;

    @Size(max = 9, message = MessageError.MAX_LIMITE)
    @Size(min = 8, message = MessageError.MIN_LIMITE)
    public String rg;

    @NotNull(message = MessageError.IS_MANDATORY)
    public EnumTipoPessoa tipoPessoa;

    @NotNull(message = MessageError.IS_MANDATORY)
    public EnumTipoCliente tipoCliente;

    @NotNull(message = MessageError.IS_MANDATORY)
    public EnderecoDto endereco;

    public ClienteDto() {
        
    }
}
