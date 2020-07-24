package com.lanchonete.apllication.dto.combo;

import java.util.List;

import com.lanchonete.apllication.dto.BaseValidate;
import com.lanchonete.domain.entities.cardapio.combo.Combo;

import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComboDto extends BaseValidate<Combo> {

    public long id;
    public String nome;
    public List<ComposicaoDto> composicao;

    @Override
    public boolean getIsValid() {
        if (this.nome == null) {
            this.SetValidation("nome", "nome está nullo");
        }
        return this.valid;
    }

    @Override
    public Combo updateEntity(Combo entity) {

        // TODO: LÓGICA UPDATE DTO
        return entity;
    }

    @Override
    public Combo createEntity(ModelMapper mapper) {
        
        return mapper.map(this, Combo.class);
    }
}
