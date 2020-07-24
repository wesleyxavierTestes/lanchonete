package com.lanchonete.apllication.dto.cliente;

import com.lanchonete.apllication.dto.BaseValidate;
import com.lanchonete.domain.enuns.cliente.EnumTipoPessoa;
import com.lanchonete.utils.ModelMapperUtils;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.cliente.Endereco;
import com.lanchonete.domain.enuns.cliente.EnumTipoCliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto extends BaseValidate {

    public long id;
    public String nome;
    public String cpf;
    public String cnjp;

    public EnumTipoPessoa tipoPessoa;
    public EnumTipoCliente tipoCliente;
    public EnderecoDto endereco;

    @Override
    public boolean getIsValid() {
        if (this.nome == null) {
            this.SetValidation("nome", "nome está nullo");
        }
        return this.valid;
    }

    public Cliente updateEntity(Cliente entity) {
        Endereco enderecoEntity = Endereco.builder().cep(this.endereco.cep).numero(this.endereco.numero)
                .logradouro(this.endereco.logradouro).complemento(this.endereco.complemento).bairro(this.endereco.bairro)
                .localidade(this.endereco.localidade).uf(this.endereco.uf).unidade(this.endereco.unidade).ibge(this.endereco.ibge)
                .gia(this.endereco.gia).build();

        entity.setId(this.id);
        entity.setNome(this.nome);
        entity.setCnjp(this.cnjp);
        entity.setCpf(this.cpf);
        entity.setTipoCliente(this.tipoCliente);
        entity.setTipoPessoa(this.tipoPessoa);
        entity.setEndereco(enderecoEntity);

        return entity;
    }
}
