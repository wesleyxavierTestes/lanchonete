package com.lanchonete.apllication.dto.produto;

import com.lanchonete.apllication.dto.BaseValidate;
import com.lanchonete.domain.entities.produto.entities.Produto;

import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto extends BaseValidate<Produto> {

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
    public Produto updateEntity(Produto entity) {

        // TODO: LÓGICA UPDATE DTO
        return entity;
    }

    @Override
    public Produto createEntity(ModelMapper mapper) {
        
        return mapper.map(this, Produto.class);
    }
}
