package com.lanchonete.domain.entities.cliente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lanchonete.domain.entities.BaseEntity;

import org.springframework.lang.NonNull;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Endereco extends BaseEntity {
    
    @NonNull
    private String cep;

    private String numero;
    
    private String logradouro;

    
    private String complemento;

    
    private String bairro;

    
    private String localidade;

    
    private String uf;

    
    private String unidade;

    
    private String ibge;

    
    private String gia;

}