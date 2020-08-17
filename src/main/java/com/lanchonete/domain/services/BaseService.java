package com.lanchonete.domain.services;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.utils.MessageError;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class BaseService<T extends BaseEntity> implements IBaseService<T> {

    private final JpaRepository<T, Long> _repository;

    public BaseService(JpaRepository<T, Long> repository) {
        _repository = repository;
    }

    @Override
    public Page<T> list(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10));
    }

    @Override
    public Page<T> listFilter(T entity, int page) {
        Example<T> example = Example.of(entity, 
        ExampleMatcher.matching().withIgnoreCase()
        .withIgnorePaths("id")
        .withIgnorePaths("dataCadastro")
        .withIgnorePaths("ativo")
        .withIgnoreNullValues()
                .withStringMatcher(StringMatcher.CONTAINING));
        return _repository.findAll(example, PageRequest.of((page - 1), 10));
    }

    @Override
    public T find(long id) {
        Optional<T> entity = _repository.findById(id);
        if (!entity.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return entity.get();
    }

    @Transactional
    @Override
    public T saveMerge(T entity) {

        _repository.save(entity);

        return entity;
    }

    @Transactional
    @Override
    public T save(T entity) {
        boolean exists = this._repository.existsById(entity.getId());
        if (exists)
            throw new RegraNegocioException(MessageError.EXISTS);

        entity.setDataCadastro(LocalDateTime.now());
        entity.setAtivo(true);

        _repository.save(entity);

        return entity;
    }

    @Transactional
    @Override
    public T update(T entity) {
        T exists = this.find(entity.getId());
        if (!Objects.nonNull(exists))
            throw new RegraNegocioException(MessageError.NOT_EXISTS);

        entity.setDataCadastro(exists.getDataCadastro());

        _repository.save(entity);

        return entity;
    }

    @Transactional
    @Override
    public T ative(long id, boolean ative) {
        T entity = this.find(id);
        if (!Objects.nonNull(entity))
            throw new RegraNegocioException(MessageError.NOT_EXISTS);

        entity.setAtivo(ative);

        _repository.save(entity);

        return entity;
    }

    @Override
    public T delete(long id) {
        T exists = this.find(id);
        if (!Objects.nonNull(exists))
            throw new RegraNegocioException(MessageError.NOT_EXISTS);

        _repository.deleteById(id);

        return exists;
    }
}
