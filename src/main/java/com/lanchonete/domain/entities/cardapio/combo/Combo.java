package com.lanchonete.domain.entities.cardapio.combo;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCombo;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoPedido;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Combo extends AbstractProduto implements IProdutoPedido, IProdutoCardapio {

    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class)
    private Set<IProdutoCombo> composicao = new HashSet<IProdutoCombo>();

    private UUID codigoCombo;
    private BigDecimal custo;
    private BigDecimal valorTotal;
}