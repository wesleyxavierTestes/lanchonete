package com.lanchonete.domain.entities.cliente;

import javax.persistence.Entity;

import com.lanchonete.domain.entities.BaseEntity;

import org.dom4j.tree.BaseElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente extends BaseEntity {
    
    private String nome;
}