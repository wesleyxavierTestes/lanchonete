package com.lanchonete.domain.services.categoria;

import java.util.Objects;

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
public class CategoriaService extends BaseService<Categoria, ICategoriaRepository> {

    @Autowired
    public CategoriaService(ICategoriaRepository repository) {
        super(repository);
    }

    public Page<CategoriaListDto> listFilterDto(Categoria entity, int page) {
        return super.listFilter(entity, page).map(Mapper.pageMap(CategoriaListDto.class));
    }

    public Page<CategoriaListDto> listDto(int page) {
        Page<Categoria> pagelist = _repository.findAll(PageRequest.of((page - 1), 10));
        return pagelist.map(Mapper.pageMap(CategoriaListDto.class));
    }

    public Page<CategoriaListDto> listActiveDto(int page) {
        return _repository.listActive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(CategoriaListDto.class));
    }

    public Page<CategoriaListDto> listDesactiveDto(int page) {
        return _repository.listDesactive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(CategoriaListDto.class));
    }

    public Categoria existsCategoriaVenda() {
        Categoria categoriaVenda = this._repository.findCategoriaVenda();
        if (!Objects.nonNull(categoriaVenda)) {
            categoriaVenda = new Categoria();
            categoriaVenda.setAtivo(true);
            categoriaVenda.setNome("Venda Automática");
            categoriaVenda = this.save(categoriaVenda);
        }
        return categoriaVenda;
    }
}