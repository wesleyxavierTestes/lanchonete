package com.lanchonete.controllers;

import java.util.Objects;

import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.domain.entities.produto.entities.Produto;
import com.lanchonete.domain.services.produto.ProdutoService;
import com.lanchonete.utils.ModelMapperUtils;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    private ModelMapper mapper = ModelMapperUtils.getInstance();

    private final ProdutoService _service;

    @Autowired
    public ProdutoController(ProdutoService service) {
        _service = service;
        mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE);
    }

    @GetMapping("list")
    public ResponseEntity<Page<Produto>> listar(@RequestParam(name = "page") int page) {
        return ResponseEntity.ok(this._service.list(page));
    }

    @GetMapping("find")
    public ResponseEntity<Produto> find(@RequestParam(name = "id") long id) {
        Produto entity = this._service.find(id);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(entity);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() ProdutoDto entityDto) {
        if (!entityDto.getIsValid())
            return ResponseEntity.badRequest().build();

        Produto entity = this._service.save(mapper.map(entityDto, Produto.class));
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(mapper.map(entity, ProdutoDto.class));
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody() ProdutoDto entityDto) {
        if (!entityDto.getIsValid())
            return ResponseEntity.badRequest().build();

        Produto entity = this._service.update(mapper.map(entityDto, Produto.class));
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(mapper.map(entity, ProdutoDto.class));
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> delete(@RequestParam(name = "id") long id) {
    Produto entity = this._service.delete(id);
    if (Objects.nonNull(entity))
        return ResponseEntity.ok(mapper.map(entity, ProdutoDto.class));
    return ResponseEntity.badRequest().build();
    }
}