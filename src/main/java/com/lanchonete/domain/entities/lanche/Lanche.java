package com.lanchonete.domain.entities.lanche;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCombo;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoComposicao;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoPedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lanche extends AbstractProduto implements IProdutoPedido, IProdutoCardapio, IProdutoCombo {

    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class, cascade = CascadeType.ALL)
    private List<IProdutoComposicao> ingredientesLanche = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal valorTotal;

    private String observacao;

    public void calcularValor() {
        if (!Objects.nonNull(this.getValor()))
            this.setValor(BigDecimal.ZERO);

        for (IProdutoComposicao i : ingredientesLanche)
            this.setValor(this.getValor().add(i.getValor()));

        if (!Objects.nonNull(this.getValorTotal())) {
            this.setValorTotal(BigDecimal.ZERO);
        }
        
        this.setValorTotal(this.getValor());
    }
}