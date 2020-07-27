package com.lanchonete.domain.services;
import com.lanchonete.domain.entities.BaseEntity;
import org.springframework.data.domain.Page;

public interface IBaseService<T extends BaseEntity> {

    Page<T> list(int page);
    T find(long id);
    T save(T entity);
    T update(T entity);
    T delete(long id) throws Exception;
}
