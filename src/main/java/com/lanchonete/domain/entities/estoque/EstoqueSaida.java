package com.lanchonete.domain.entities.estoque;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.lanchonete.domain.entities.venda.Venda;

@Entity
@DiscriminatorValue("S")
public class EstoqueSaida extends AbstractEstoque {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Venda venda;
}