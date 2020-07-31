package com.lanchonete.domain.entities.produto.baseentity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractProduto extends BaseEntity implements IProduto {

    @Column(nullable = false)
    private UUID codigo;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Categoria categoria;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private EnumTipoProduto tipoProduto;

    public IProduto setTipoProduto(EnumTipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
        return this;
    }
}