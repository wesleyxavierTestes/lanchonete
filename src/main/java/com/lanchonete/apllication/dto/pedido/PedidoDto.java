package com.lanchonete.apllication.dto.pedido;

import com.lanchonete.apllication.dto.BaseValidate;
import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.domain.entities.pedido.PedidoAguardando;

import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto extends BaseValidate<Pedido> {

    public long id;
    public String nome;

    @Override
    public boolean getIsValid() {
        if (this.nome == null) {
            this.SetValidation("nome", "nome está nullo");
        }
        return this.valid;
    }

    @Override
    public Pedido updateEntity(Pedido entity) {

        // TODO: LÓGICA UPDATE DTO
        return entity;
    }

    @Override
    public PedidoAguardando createEntity(ModelMapper mapper) {
        
        return mapper.map(this, PedidoAguardando.class);
    }
}
