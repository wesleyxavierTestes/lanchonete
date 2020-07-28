package com.lanchonete.domain.entities.cardapio.combo;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCombo;
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

    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class,
    cascade = CascadeType.DETACH)
    private Set<IProdutoCombo> composicao = new HashSet<>();

    @Column(nullable = false)
    private BigDecimal valorTotal;

    public void calcularValorTotal() {
        this.valorTotal = BigDecimal.ZERO;
        for (IProdutoCombo i : composicao)
            this.valorTotal = this.valorTotal.add(i.getValor());
    }
}