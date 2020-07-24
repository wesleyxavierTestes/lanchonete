package com.lanchonete.domain.entities.estoque;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.entities.produto.entities.Produto;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@DiscriminatorColumn(name = "estoque_tipo")
public abstract class AbstractEstoque extends BaseEntity implements IEstoque {

  @ManyToOne
  private Produto produto;
  private BigDecimal valor;

}
