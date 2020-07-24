package com.lanchonete.domain.entities.estoque;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("E")
public class EstoqueEntrada extends AbstractEstoque {
  private BigDecimal valorAnterior;
}