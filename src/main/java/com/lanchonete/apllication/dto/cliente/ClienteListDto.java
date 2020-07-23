package com.lanchonete.apllication.dto.cliente;

import javax.validation.constraints.NotNull;

import com.lanchonete.apllication.dto.BaseValidate;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.cliente.EnumTipoPessoa;
import com.lanchonete.domain.enuns.cliente.EnumTipoCliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteListDto extends BaseValidate {
    
    public String nome;
    public String cpfCnpj;
    public String tipoPessoa;            
    public String tipoCliente;   
    public String enderecoNome;

    @Override
    public boolean getIsValid() {
        if (this.nome == null) {
            this.SetValidation("nome", "nome está nullo");
        }
        return this.valid;
    }

    public static ClienteListDto converter(Cliente cli) 
    {
        return ClienteListDto.builder()
        .nome(cli.getNome())
        .cpfCnpj(cli.getCpfCnpj())
        .build();
    }

}
