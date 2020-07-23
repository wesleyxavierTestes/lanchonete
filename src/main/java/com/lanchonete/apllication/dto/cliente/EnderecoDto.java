package com.lanchonete.apllication.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDto {
    public String cep;
    public String numero;
    public String logradouro;
    public String complemento;
    public String bairro;
    public String localidade;
    public String uf;
    public String unidade;
    public String ibge;
    public String gia;

    public boolean getIsValid() {
        return this.cep != null;
    }
}