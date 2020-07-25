package com.lanchonete.domain.services.cliente;

import java.util.List;
import java.util.Objects;

import com.lanchonete.apllication.dto.cliente.ClienteListDto;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.cliente.Endereco;
import com.lanchonete.domain.enuns.cliente.EnumTipoPessoa;
import com.lanchonete.domain.enuns.cliente.EnumTipoCliente;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.cliente.IClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ClienteService extends BaseService<Cliente> {

    private final IClienteRepository _repository;

    @Autowired
    public ClienteService(IClienteRepository repository) {
        super(repository);
        _repository = repository;
    }

    public Page<Cliente> listFilter(int page) {
        return this._repository.findAll(PageRequest.of((page - 1), 10));
    }

    public void createClienteDefault(Cliente entity) {
        String nome = "";

        Endereco endereco = Endereco.builder().cep("01001000").bairro("Sé").complemento("lado ímpar").numero("657")
                .gia("1004").ibge("3550308").localidade("São Paulo").logradouro("Praça da Sé").uf("SP")
                .unidade("unidade").build();

        if (entity.getClass() != null) {
            nome = entity.getNome();
        }

        entity.setNome(nome);
        entity.setCpf("00000000000");
        entity.setCnjp(null);
        entity.setTipoCliente(EnumTipoCliente.ConsumidorFinal);
        entity.setTipoPessoa(EnumTipoPessoa.Fisica);
        entity.setEndereco(endereco);
    }

    public boolean existeClientePadrao() {
        List<Cliente> entity = _repository.findByTipoCliente("ConsumidorFinal");
        return Objects.nonNull(entity) && !entity.isEmpty();
    }

	public Page<Cliente> listSpendMore(int page) {
        Page<Cliente> entity = null;
         //  _repository.listSpendMore("ConsumidorFinal");
        return entity;
    }
    
    public Page<ClienteListDto> listDto(int page) {
        return _repository.findAllDto(PageRequest.of((page - 1), 10));
    }
}