package com.lanchonete.infra.repositorys;

import java.util.Optional;

import com.lanchonete.domain.entities.IBaseEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseRepository<T extends IBaseEntity> extends JpaRepository<T, Long> {

    Optional<T> findByIdEquals(long id);
}