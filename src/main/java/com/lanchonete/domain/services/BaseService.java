package com.lanchonete.domain.services;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.domain.entities.IBaseEntity;
import com.lanchonete.infra.repositorys.IBaseRepository;
import com.lanchonete.utils.MessageError;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public abstract class BaseService<T extends IBaseEntity, Y extends IBaseRepository<T>> {

    protected final Y _repository;

    public BaseService(Y repository) {
        _repository = repository;
    }

    public Page<T> list(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10));
    }

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
    
    public T find(long id) {
        Optional<T> entity = _repository.findByIdEquals(id);
        
         if (!entity.isPresent())
            throw new RegraNegocioException("Item inexistente");
        return entity.get();
    }

    @Transactional
    public T saveMerge(T entity) {

        _repository.save(entity);

        return entity;
    }

    @Transactional
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
    public T update(T entity) {
        T exists = this.find(entity.getId());
        if (!Objects.nonNull(exists))
            throw new RegraNegocioException(MessageError.NOT_EXISTS);

        entity.setDataCadastro(exists.getDataCadastro());

        _repository.save(entity);

        return entity;
    }

    @Transactional
    public T ative(long id, boolean ative) {
        T entity = this.find(id);
        if (!Objects.nonNull(entity))
            throw new RegraNegocioException(MessageError.NOT_EXISTS);

        entity.setAtivo(ative);

        _repository.save(entity);

        return entity;
    }

    public T delete(long id) {
        T exists = this.find(id);
        if (!Objects.nonNull(exists))
            throw new RegraNegocioException(MessageError.NOT_EXISTS);

        _repository.deleteById(id);

        return exists;
    }
}
