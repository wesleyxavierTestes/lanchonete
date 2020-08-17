package com.lanchonete.domain.services.produto;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.lanchonete.apllication.dto.produto.ProdutoListDto;
import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService extends BaseService<Produto> {

    private final IProdutoRepository _repository;

    @Autowired
    public ProdutoService(IProdutoRepository repository) {
        super(repository);
        _repository = repository;
    }

    public Produto findByEstoque(long id) {
        Produto entity = this.find(id);
        if (Objects.nonNull(entity)) {
            double count = this._repository.countEstoqueById(entity.getId());
            entity.setEstoqueAtual(count);
            return entity;
        }
        return null;
    }

    public Page<ProdutoListDto> listFilterDto(Produto entity, int page) {
        return super.listFilter(entity, page).map(produto -> {
            double count = this._repository.countEstoqueById(produto.getId());
            produto.setEstoqueAtual(count);
            return produto;
        }).map(Mapper.pageMap(ProdutoListDto.class));
    }

    public Page<ProdutoListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10)).map(produto -> {
            double count = this._repository.countEstoqueById(produto.getId());
            produto.setEstoqueAtual(count);
            return produto;
        }).map(Mapper.pageMap(ProdutoListDto.class));
    }

    public Page<ProdutoListDto> listActiveDto(int page) {
        return _repository.listActive(PageRequest.of((page - 1), 10)).map(produto -> {
            double count = this._repository.countEstoqueById(produto.getId());
            produto.setEstoqueAtual(count);
            return produto;
        }).map(Mapper.pageMap(ProdutoListDto.class));
    }

    public Page<ProdutoListDto> listDesactiveDto(int page) {
        return _repository.listDesactive(PageRequest.of((page - 1), 10)).map(produto -> {
            double count = this._repository.countEstoqueById(produto.getId());
            produto.setEstoqueAtual(count);
            return produto;
        }).map(Mapper.pageMap(ProdutoListDto.class));
    }

    public Page<ProdutoListDto> listEstoqueZero(int page) {
        return _repository.listEstoque(0, 1, PageRequest.of((page - 1), 10)).map(produto -> {
            double count = this._repository.countEstoqueById(produto.getId());
            produto.setEstoqueAtual(count);
            return produto;
        }).map(Mapper.pageMap(ProdutoListDto.class));
    }

    public Page<ProdutoListDto> listEstoque(int page) {
        return _repository.listEstoque(1, 9999999, PageRequest.of((page - 1), 10)).map(produto -> {
            double count = this._repository.countEstoqueById(produto.getId());
            produto.setEstoqueAtual(count);
            return produto;
        }).map(Mapper.pageMap(ProdutoListDto.class));
    }

    public long countEstoqueByCodigo(String codigo) {
       return this._repository.countEstoqueByCodigo(UUID.fromString(codigo));
    }

    public Collection<Produto> teste(String param) {
        // return this._repository.findByCodigo(UUID.fromString(param));
        return this._repository.findByNomeContaining(param);
     }

    public Produto produtoCodigo(String codigo) {
        Optional<Produto> produtoOptional = this._repository._produtoCodigo(UUID.fromString(codigo)).stream()
                .findFirst();
        if (!produtoOptional.isPresent()) {
            throw new RegraNegocioException("Produto Inexistente");
        }

        return produtoOptional.get();
    }
}