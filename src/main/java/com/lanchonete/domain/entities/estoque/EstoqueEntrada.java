package com.lanchonete.domain.entities.estoque;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.lanchonete.domain.entities.produto.entities.Produto;

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
    if (this.quantidade < 0)
      this.quantidade *= 1;
  }

  public static EstoqueEntrada ProdutoSave(Produto entity) {
    EstoqueEntrada entityEstoque = new EstoqueEntrada();
    entityEstoque.setProduto(entity);
    entityEstoque.setQuantidade(0);
    return entityEstoque;
  }
}