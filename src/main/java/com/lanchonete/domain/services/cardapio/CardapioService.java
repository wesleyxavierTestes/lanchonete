package com.lanchonete.domain.services.cardapio;

import java.util.Objects;

import com.lanchonete.apllication.dto.cardapio.CardapioListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.cardapio.ICardapioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CardapioService extends BaseService<Cardapio> {

    private final ICardapioRepository _repository;

    @Autowired
    public CardapioService(ICardapioRepository repository) {
        super(repository);
        _repository = repository;
    }

    public Page<Cardapio> listFilter(int page) {
        return this._repository.findAll(PageRequest.of((page - 1), 10));
    }

    public Cardapio findByName(String nome) {
        Cardapio entity = _repository.findByName(nome);
        return (Objects.nonNull(entity)) ? entity : null;
    }

    public Page<CardapioListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(CardapioListDto.class));
    }

    public Page<CardapioListDto> listActiveDto(int page) {
        return _repository.listActive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(CardapioListDto.class));
    }

    public Page<CardapioListDto> listDesactiveDto(int page) {
        return _repository.listDesactive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(CardapioListDto.class));
    }
}