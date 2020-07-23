package com.lanchonete.domain.services;

import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;

import com.lanchonete.domain.entities.BaseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

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
    public T find(long id) {
        Optional<T> entity = _repository.findById(id);
        if (entity.isPresent())
            return entity.get();
        return null;
    }

    @Override
    public T save(T entity) {
        try {
            entity.setDataCadastro(Calendar.getInstance());
            T entitySave = _repository.save(entity);
            return entitySave;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public T update(T entity) {
        T exists = this.find(entity.getId());
        if (!Objects.nonNull(exists))
            return null;
        entity.setDataCadastro(exists.getDataCadastro());
        try {
            T entitySave = _repository.save(entity);
            return entitySave;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public T delete(long id) {
        T exists = this.find(id);
        if (!Objects.nonNull(exists))
            return null;
        try {
            _repository.deleteById(id);
            return exists;
        } catch (Exception e) {
            return null;
        }
    }
}
