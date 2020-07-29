package com.lanchonete.apllication.dto.combo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComboListDto {

    public long id;
    public String nome;
    public String valor;
    public String valorTotal;
}
