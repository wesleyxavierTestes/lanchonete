package com.lanchonete.domain.entities.estoque;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.entities.produto.entities.Produto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity(name = "estoque")
@Table(name = "estoque")
@DiscriminatorColumn(name = "estoque_tipo")
public abstract class AbstractEstoque extends BaseEntity {

  @ManyToOne(fetch = FetchType.EAGER)
  protected Produto produto;
  protected BigDecimal valor;

  /**
   * Configuração para salvar
   * Ignorar update
   */
  public abstract void configureSave();

}
