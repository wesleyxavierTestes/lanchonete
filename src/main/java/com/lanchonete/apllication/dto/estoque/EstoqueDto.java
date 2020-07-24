package com.lanchonete.apllication.dto.estoque;

import java.util.Calendar;

import com.lanchonete.apllication.dto.BaseValidate;
import com.lanchonete.domain.entities.estoque.AbstractEstoque;

import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueDto extends BaseValidate<AbstractEstoque> {

    public long idProduto;
    public long quantidade;
    public Calendar data;

    @Override
    public boolean getIsValid() {

        return this.valid;
    }

    @Override
    public AbstractEstoque updateEntity(AbstractEstoque entity) {

        // TODO: LÓGICA UPDATE DTO
        return entity;
    }

    @Override
    public AbstractEstoque createEntity(ModelMapper mapper) {
        
        return mapper.map(this, AbstractEstoque.class);
    }
}
