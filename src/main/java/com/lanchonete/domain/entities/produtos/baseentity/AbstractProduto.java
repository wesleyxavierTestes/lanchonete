package com.lanchonete.domain.entities.produtos.baseentity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.enuns.EnumProdutoTipo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractProduto extends BaseEntity {

    @Column()
    private UUID codigo;

    @Column()
    private EnumProdutoTipo produtoTipo;

    private String nome;

    private BigDecimal valor;
    private BigDecimal custo;
}