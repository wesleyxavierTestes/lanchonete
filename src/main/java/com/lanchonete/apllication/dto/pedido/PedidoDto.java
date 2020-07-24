package com.lanchonete.apllication.dto.pedido;

import com.lanchonete.apllication.dto.BaseValidate;
import com.lanchonete.domain.entities.pedido.Pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto extends BaseValidate {

    public long id;
    public String nome;

    @Override
    public boolean getIsValid() {
        if (this.nome == null) {
            this.SetValidation("nome", "nome está nullo");
        }
        return this.valid;
    }

    public Pedido updateEntity(Pedido entity) {
        

        return entity;
    }
}
