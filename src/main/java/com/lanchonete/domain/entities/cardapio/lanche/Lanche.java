package com.lanchonete.domain.entities.cardapio.lanche;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoComposicao;
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
public class Lanche extends AbstractProduto implements IProdutoPedido, IProdutoCardapio {

    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class, cascade = CascadeType.DETACH)
    private Set<IProdutoComposicao> ingredientesLanche = new HashSet<>();

    @Column(nullable = true)
    private BigDecimal desconto;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    public void calcularValorTotal() {
        this.setValor(BigDecimal.ZERO);
        if (!Objects.nonNull(this.desconto))
            this.setDesconto(BigDecimal.ZERO);

        for (IProdutoComposicao i : ingredientesLanche)
            this.setValor(this.getValor().add(i.getValor()));

        this.valorTotal = this.getValor().subtract(this.desconto);
    }
}