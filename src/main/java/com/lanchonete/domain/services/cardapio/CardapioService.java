package com.lanchonete.domain.services.cardapio;

import java.util.List;
import java.util.Objects;

import com.lanchonete.apllication.dto.cardapio.CardapioListDto;
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

    public boolean existeCardapioPadrao() {
        List<Cardapio> entity = _repository.findByTipoCardapio("ConsumidorFinal");
        return Objects.nonNull(entity) && !entity.isEmpty();
    }

	public Page<Cardapio> listSpendMore(int page) {
        Page<Cardapio> entity = null;
         //  _repository.listSpendMore("ConsumidorFinal");
        return entity;
    }
    
    public Page<CardapioListDto> listDto(int page) {
        return _repository.findAllDto(PageRequest.of((page - 1), 10));
    }

	public Page<CardapioListDto> listActiveDto(int page) {
		return this._repository.listActiveDto(PageRequest.of((page - 1), 10));
	}

	public Page<CardapioListDto> listDesactiveDto(int page) {
		return this._repository.listDesactiveDto(PageRequest.of((page - 1), 10));
	}
}