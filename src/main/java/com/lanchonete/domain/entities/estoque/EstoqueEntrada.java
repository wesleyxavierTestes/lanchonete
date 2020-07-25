package com.lanchonete.domain.entities.estoque;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("E")
public class EstoqueEntrada extends AbstractEstoque {
  private BigDecimal valorAnterior;

  @Override
  public void configureSave() {
    this.valor = this.valor.multiply(BigDecimal.valueOf(1));
  }
}