package com.lanchonete.domain.entities.cardapio.combo;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.lanchonete.domain.entities.cardapio.lanche.Lanche;
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

    @OneToOne
    private Lanche lanche;

    @OneToOne
    private ComboBebida bebida;

    public void calcularValor() {
        this.setValor(this.lanche.getValorTotal().add(this.bebida.getValor()));
    }
}