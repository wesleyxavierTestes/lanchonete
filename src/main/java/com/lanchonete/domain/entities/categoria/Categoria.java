package com.lanchonete.domain.entities.categoria;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.lanchonete.domain.entities.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Categoria extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String nome;
}