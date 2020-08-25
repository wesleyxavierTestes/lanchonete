package com.lanchonete.apllication.mappers;

import com.lanchonete.apllication.dto.cliente.ClienteDto;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.cliente.Endereco;

final class ClienteMapper {
    private ClienteMapper() {}
    public static Cliente update(ClienteDto entityDto, Cliente entity) {
        Endereco enderecoEntity = Endereco.builder().cep(entityDto.endereco.cep).numero(entityDto.endereco.numero)
                .logradouro(entityDto.endereco.logradouro).complemento(entityDto.endereco.complemento).bairro(entityDto.endereco.bairro)
                .localidade(entityDto.endereco.localidade).uf(entityDto.endereco.uf).unidade(entityDto.endereco.unidade).ibge(entityDto.endereco.ibge)
                .gia(entityDto.endereco.gia).build();

        entity.setId(entityDto.id);
        entity.setNome(entityDto.nome);
        entity.setCnjp(entityDto.cnpj);
        entity.setCpf(entityDto.cpf);
        entity.setTipoCliente(entityDto.tipoCliente);
        entity.setTipoPessoa(entityDto.tipoPessoa);
        entity.setEndereco(enderecoEntity);

        return entity;
    }
}