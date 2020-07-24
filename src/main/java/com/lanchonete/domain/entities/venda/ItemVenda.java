package com.lanchonete.domain.entities.venda;

import java.math.BigDecimal;

import javax.persistence.Entity;

import com.lanchonete.domain.entities.produtos.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produtos.baseentity.IProdutoVenda;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItemVenda extends AbstractProduto implements IProdutoVenda {

    private BigDecimal valorDesconto;
    private BigDecimal valorTotal;
}