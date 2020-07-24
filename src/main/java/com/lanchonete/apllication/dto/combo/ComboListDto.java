package com.lanchonete.apllication.dto.combo;

import com.lanchonete.apllication.dto.BaseValidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComboListDto extends BaseValidate {

    public String nome;

    @Override
    public boolean getIsValid() {
        if (this.nome == null) {
            this.SetValidation("nome", "nome está nullo");
        }
        return this.valid;
    }
}
