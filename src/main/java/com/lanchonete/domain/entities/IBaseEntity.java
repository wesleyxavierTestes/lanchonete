package com.lanchonete.domain.entities;

import java.time.LocalDateTime;

public interface IBaseEntity {
    
    public long getId();

    public void setAtivo(boolean ativo);

    public void setId(long id);

    public LocalDateTime getDataCadastro();

    public void setDataCadastro(LocalDateTime dataCadastro);
}