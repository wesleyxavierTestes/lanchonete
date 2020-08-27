package com.lanchonete.domain.services.cliente;

import com.lanchonete.domain.entities.cliente.Endereco;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.cliente.IEnderecoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class EnderecoService extends BaseService<Endereco, IEnderecoRepository> {

    @Autowired
    public EnderecoService(IEnderecoRepository repository) {
        super(repository);
    }

    public Page<Endereco>  listFilter(int page) {
        return this._repository.findAll(PageRequest.of((page - 1), 10));
    }
}