package com.lanchonete.domain.entities.cliente;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import com.lanchonete.domain.enuns.cliente.EnumTipoPessoa;
import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.enuns.cliente.EnumTipoCliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente extends BaseEntity {
    
    private String nome;

    private String cpf;
    
    private String cnjp;

    @Enumerated(EnumType.STRING)
    private EnumTipoPessoa tipoPessoa;
            
    @Enumerated(EnumType.STRING)
    private EnumTipoCliente tipoCliente;
   
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Endereco endereco;

	public String getCpfCnpj() {
        return this.tipoPessoa == EnumTipoPessoa.Fisica
                ? this.cpf
                : this.cnjp;
    }
}