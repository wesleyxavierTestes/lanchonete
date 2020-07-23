package com.lanchonete.domain.entities.cliente;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;

import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.entities.produtos.entities.Lanche;
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
    
    @Min(2)
    @Column(nullable = false)
    private String nome;

    @Min(8)
    @Column(nullable = false)
    private String cpf;
    
    @Min(9)
    @Column(nullable = false)
    private String cnjp;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumTipoPessoa tipoPessoa;
            
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumTipoCliente tipoCliente;
   
    @OneToOne
    private Endereco endereco;

	public String getCpfCnpj() {
        return this.tipoPessoa == EnumTipoPessoa.Fisica
                ? this.cpf
                : this.cnjp;
	}
}