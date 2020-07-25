package com.lanchonete.apllication.dto.cliente;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDto  {
    
    public long id;
    
    @NotNull(message = "Name is mandatory")
    public String cep;

    @NotNull(message = "Name is mandatory")
    public String numero;

    @NotNull(message = "Name is mandatory")
    public String logradouro;

    public String complemento;

    @NotNull(message = "Name is mandatory")
    public String bairro;

    @NotNull(message = "Name is mandatory")
    public String localidade;

    @NotNull(message = "Name is mandatory")
    public String uf;
    public String unidade;
    public String ibge;
    public String gia;

}