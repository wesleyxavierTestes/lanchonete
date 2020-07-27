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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente extends BaseEntity {
    
    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = true, length = 100)
    private String email;

    @Column(nullable = true, length = 9)
    private String rg;

    @Column(nullable = true, length = 11)
    private String cpf;
    
    @Column(nullable = true, length = 14)
    private String cnjp;

    @Enumerated(EnumType.STRING)
    private EnumTipoPessoa tipoPessoa;
            
    @Enumerated(EnumType.STRING)
    private EnumTipoCliente tipoCliente;
   
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Endereco endereco;
}