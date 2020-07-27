package com.lanchonete.domain.services;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.utils.MessageError;

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

    @Transactional
    @Override
    public T save(T entity) {
        boolean exists = this._repository.existsById(entity.getId());
        if (exists)
            return null;

        entity.setDataCadastro(LocalDateTime.now());
        entity.setAtivo(true);

        T entitySave = _repository.save(entity);
        return entitySave;
    }

    @Transactional
    @Override
    public T update(T entity) {
        T exists = this.find(entity.getId());
        if (!Objects.nonNull(exists))
            return null;

        entity.setDataCadastro(exists.getDataCadastro());

        T entitySave = _repository.save(entity);
        return entitySave;
    }

    @Override
    public T delete(long id) throws Exception {
        try {
            T exists = this.find(id);
            if (!Objects.nonNull(exists))
                throw new RegraNegocioException(null);
            _repository.deleteById(id);
            return exists;
        } catch (RegraNegocioException e) {
            throw new RegraNegocioException(MessageError.NOT_EXISTS);
        }
    }
}
