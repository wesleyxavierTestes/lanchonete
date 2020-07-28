package com.lanchonete.domain.services.categoria;

import com.lanchonete.apllication.dto.categoria.CategoriaListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.categoria.ICategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService extends BaseService<Categoria> {

    private final ICategoriaRepository _repository;

    @Autowired
    public CategoriaService(ICategoriaRepository repository) {
        super(repository);
        _repository = repository;
    }

    public Page<CategoriaListDto>  listFilterDto(Categoria entity, int page) {
        return super. listFilter(entity, page)
        .map(Mapper.pageMap(CategoriaListDto.class));
    }

    public Page<CategoriaListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(CategoriaListDto.class));
    }

    public Page<CategoriaListDto> listActiveDto(int page) {
        return _repository.listActive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(CategoriaListDto.class));
    }

    public Page<CategoriaListDto> listDesactiveDto(int page) {
        return _repository.listDesactive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(CategoriaListDto.class));
    }
}