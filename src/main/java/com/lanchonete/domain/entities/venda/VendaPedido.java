package com.lanchonete.domain.entities.venda;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.entities.pedido.Pedido;

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
public class VendaPedido  extends BaseEntity {
    
    @OneToOne
    private Pedido pedido;

    @ManyToOne
    private Venda venda;
}