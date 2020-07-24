package com.lanchonete.apllication.dto.cliente;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDto {
    
    @NotNull
    public String cep;

    @NotNull
    public String numero;

    @NotNull
    public String logradouro;

    public String complemento;

    @NotNull
    public String bairro;

    @NotNull
    public String localidade;

    @NotNull
    public String uf;
    public String unidade;
    public String ibge;
    public String gia;

    public boolean getIsValid() {
        return this.cep != null;
    }
}