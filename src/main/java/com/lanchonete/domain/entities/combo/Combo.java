package com.lanchonete.domain.entities.combo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.lanchonete.domain.entities.bebida.Bebida;
import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.entities.lanche.Lanche;
import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoPedido;

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
public class Combo extends AbstractProduto implements IProdutoPedido, IProdutoCardapio {

    @ManyToMany
    @JoinTable(name = "combo_lanche", 
        joinColumns = @JoinColumn(name = "lanche_id"), 
        inverseJoinColumns = @JoinColumn(name = "combo_id")
    )
    private List<Lanche> lanches = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "combo_bebida", 
        joinColumns = @JoinColumn(name = "bebida_id"), 
        inverseJoinColumns = @JoinColumn(name = "combo_id")
    )
    private List<Bebida> bebidas = new ArrayList<>();
    
    @Column(nullable = false)
    private BigDecimal valorTotal;

    private String observacao;

    public void calcularValor() {
        if (!Objects.nonNull(this.getValor()))
            this.setValor(BigDecimal.ZERO);

        BigDecimal lanche = BigDecimal.ZERO;
        for (Lanche _lanche : this.lanches) {
            lanche.add(_lanche.getValorTotal());
        }

        BigDecimal bebida = BigDecimal.ZERO;
        for (Bebida _bebida : this.bebidas) {
            bebida.add(_bebida.getValor());
        }

        this.setValor(lanche.add(bebida));

        if (!Objects.nonNull(this.getValorTotal()))
            this.setValorTotal(this.getValor());
    }
}