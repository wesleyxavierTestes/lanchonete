package com.lanchonete.apllication.dto.cliente;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDto  {
    
    public long id;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Size(max = 8, min = 8, message = MessageError.IS_INVALID)
    public String cep;

    @Size(max = 50, message = MessageError.IS_INVALID)
    public String numero;
    
    @NotNull(message = MessageError.IS_MANDATORY)
    @Size(max = 100, min = 2, message = MessageError.IS_INVALID)
    public String logradouro;

    @Size(max = 100, message = MessageError.IS_INVALID)
    public String complemento;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Size(max = 200, min = 2, message = MessageError.IS_INVALID)
    public String bairro;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Size(max = 50, min = 2, message = MessageError.IS_INVALID)
    public String localidade;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Size(max = 2, min = 2, message = MessageError.IS_INVALID)
    public String uf;

    @Size(max = 30, message = MessageError.IS_INVALID)
    public String unidade;

    @Size(max = 30, message = MessageError.IS_INVALID)
    public String ibge;

    @Size(max = 30, message = MessageError.IS_INVALID)
    public String gia;
}

