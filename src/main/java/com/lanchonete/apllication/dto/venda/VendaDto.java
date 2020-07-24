package com.lanchonete.apllication.dto.venda;

import com.lanchonete.apllication.dto.BaseValidate;
import com.lanchonete.domain.entities.venda.Venda;

import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendaDto extends BaseValidate<Venda> {

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
    public Venda updateEntity(Venda entity) {

        // TODO: LÓGICA UPDATE DTO
        return entity;
    }

    @Override
    public Venda createEntity(ModelMapper mapper) {
        
        return mapper.map(this, Venda.class);
    }
}
