package com.lanchonete.domain.entities.cardapio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

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

    @Column(unique = true)
    private String nome;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = AbstractProduto.class,
    cascade = CascadeType.ALL)
    private List<IProdutoCardapio> itensDisponiveis = new ArrayList<>();
}