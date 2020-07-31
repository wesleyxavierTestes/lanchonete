package com.lanchonete.domain.services;
import com.lanchonete.domain.entities.BaseEntity;
import org.springframework.data.domain.Page;

public interface IBaseService<T extends BaseEntity> {

    Page<T> list(int page);
    Page<T>  listFilter(T entity, int page);
    T find(long id);
    T saveMerge(T entity);
    T save(T entity);
    T update(T entity);
    T delete(long id) throws Exception;
    T ative(long id, boolean ative);
}
