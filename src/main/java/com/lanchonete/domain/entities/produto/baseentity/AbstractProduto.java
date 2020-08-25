package com.lanchonete.domain.entities.produto.baseentity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.domain.entities.venda.Venda;
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

    @ManyToMany
    @JoinTable(
        name = "pedido_itens",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Pedido> pedidos;

    @ManyToMany
    @JoinTable(name = "cardapio_itens", 
        joinColumns = @JoinColumn(name = "item_id"), 
        inverseJoinColumns = @JoinColumn(name = "cardapio_id")
    )
    private List<Cardapio> products = new ArrayList<>();

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private EnumTipoProduto tipoProduto = EnumTipoProduto.Outros;

    public IProduto setTipoProduto(EnumTipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
        return this;
    }
}