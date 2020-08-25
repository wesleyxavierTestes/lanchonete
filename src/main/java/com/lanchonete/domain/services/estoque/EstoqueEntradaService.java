package com.lanchonete.domain.services.estoque;

import com.lanchonete.apllication.dto.estoque.EstoqueListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.estoque.EstoqueEntrada;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.estoque.IEstoqueEntradaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EstoqueEntradaService extends BaseService<EstoqueEntrada, IEstoqueEntradaRepository> {
	
    @Autowired
    public EstoqueEntradaService(final IEstoqueEntradaRepository repository) {
        super(repository);
    }

    public Page<EstoqueListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(EstoqueListDto.class));
    }
}