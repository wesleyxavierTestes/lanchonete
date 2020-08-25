package com.lanchonete.domain.entities.cardapio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;

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
public class Cardapio extends BaseEntity {

    private String nome;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = AbstractProduto.class)
    @JoinTable( name = "cardapio_itens",
        joinColumns=@JoinColumn(name="cardapio_id"),
        inverseJoinColumns=@JoinColumn(name="item_id")
    )
    private List<IProdutoCardapio> itensDisponiveis = new ArrayList<>();
}