package com.lanchonete.domain.entities.produto.baseentity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.enuns.EnumProdutoTipo;

import org.modelmapper.ModelMapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractProduto extends BaseEntity implements IProduto {

    @Column()
    private UUID codigo;

    @Column()
    private EnumProdutoTipo produtoTipo;

    private String nome;

    private BigDecimal valor;
    private BigDecimal custo;

    public <T extends IProduto> T convert(Class<T> type) {
        ModelMapper mapper = new ModelMapper();
        T i = mapper.map(this, type);
        return i;
    }
}