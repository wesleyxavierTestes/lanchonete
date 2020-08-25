package com.lanchonete.domain.services.cliente;

import java.util.Objects;

import com.lanchonete.apllication.dto.cliente.ClienteListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.cliente.Endereco;
import com.lanchonete.domain.enuns.cliente.EnumTipoCliente;
import com.lanchonete.domain.enuns.cliente.EnumTipoPessoa;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.cliente.IClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ClienteService extends BaseService<Cliente, IClienteRepository> {

    @Autowired
    public ClienteService(IClienteRepository repository) {
        super(repository);
    }

    public Page<ClienteListDto>  listFilterDto(Cliente entity, int page) {
        return super. listFilter(entity, page).map(Mapper.pageMap(ClienteListDto.class));
    }

    public void createClienteDefault(Cliente entity) {
        String nome = "";

        Endereco endereco = Endereco.builder().cep("01001000").bairro("Sé").complemento("lado ímpar").numero("657")
                .gia("1004").ibge("3550308").localidade("São Paulo").logradouro("Praça da Sé").uf("SP")
                .unidade("unidade").build();

        if (!Objects.nonNull(entity.getNome()) || entity.getNome().isEmpty()) {
            nome = "Consumidor final";
        }

        entity.setNome(nome);
        entity.setCpf("00000000000");
        entity.setCnjp(null);
        entity.setTipoCliente(EnumTipoCliente.ConsumidorFinal);
        entity.setTipoPessoa(EnumTipoPessoa.Fisica);
        entity.setEndereco(endereco);
    }

    public boolean existeClientePadrao() {
        Cliente entity = this.findDefault();
        return Objects.nonNull(entity);
    }

    public Page<ClienteListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10, Sort.by(Direction.ASC, "nome"))).map(Mapper.pageMap(ClienteListDto.class));
    }

    public Page<ClienteListDto> listActiveDto(int page) {
        return _repository.listActive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(ClienteListDto.class));
    }

    public Page<ClienteListDto> listDesactiveDto(int page) {
        return _repository.listDesactive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(ClienteListDto.class));
    }

    public Cliente findDefault() {
        Cliente entity = _repository.findByTipoCliente("ConsumidorFinal");
        return entity;
    }

    public Page<ClienteListDto> listSpendMore(int page) {
        return _repository.listSpendMore(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(ClienteListDto.class));
    }
}