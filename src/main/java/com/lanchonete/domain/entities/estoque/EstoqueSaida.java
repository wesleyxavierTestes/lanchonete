package com.lanchonete.domain.entities.estoque;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.lanchonete.domain.entities.venda.Venda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("S")
public class EstoqueSaida extends AbstractEstoque {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Venda venda;

    @Override
    public void configureSave() {
        if (this.quantidade > 0)
        this.quantidade = this.quantidade *= -1;
    }
}