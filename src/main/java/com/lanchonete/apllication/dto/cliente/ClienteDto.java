package com.lanchonete.apllication.dto.cliente;

import javax.validation.constraints.NotNull;

import com.lanchonete.apllication.dto.BaseValidate;
import com.lanchonete.domain.entities.cliente.EnumTipoPessoa;
import com.lanchonete.domain.enuns.cliente.EnumTipoCliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto extends BaseValidate {
    

    private String nome;
    private String cpf;

    private String cnjp;
    private EnumTipoPessoa tipoPessoa;            
    private EnumTipoCliente tipoCliente;   
    // private EnderecoDto endereco;

    @Override
    public boolean getIsValid() {
        if (this.nome == null) {
            this.SetValidation("nome", "nome está nullo");
        }
        return this.valid;
    }
}
