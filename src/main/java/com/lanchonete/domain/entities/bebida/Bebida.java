package com.lanchonete.domain.entities.bebida;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.lanchonete.domain.entities.combo.Combo;
import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCombo;
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
public class Bebida extends AbstractProduto implements IProdutoPedido, IProdutoCardapio, IProdutoCombo {

    @ManyToMany
    @JoinTable(name = "combo_bebida", 
        joinColumns = @JoinColumn(name = "combo_id"), 
        inverseJoinColumns = @JoinColumn(name = "bebida_id")
    )
    private List<Combo> bebidas = new ArrayList<>();
}