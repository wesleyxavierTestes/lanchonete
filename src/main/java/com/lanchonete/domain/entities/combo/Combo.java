package com.lanchonete.domain.entities.combo;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.lanchonete.domain.entities.bebida.Bebida;
import com.lanchonete.domain.entities.lanche.Lanche;
import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoPedido;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoVenda;

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
public class Combo extends AbstractProduto implements  IProdutoPedido, IProdutoCardapio, IProdutoVenda {

    @OneToOne
    private Lanche lanche;

    @OneToOne(cascade = CascadeType.ALL)
    private Bebida bebida;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    private String observacao;

    public void calcularValor() {
        if (!Objects.nonNull(this.getValor()))
            this.setValor(BigDecimal.ZERO);

        this.setValor(this.lanche.getValorTotal().add(this.bebida.getValor()));
    }
}