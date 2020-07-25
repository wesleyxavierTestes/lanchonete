package com.lanchonete.controllers;

import java.util.Objects;

import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.dto.produto.ProdutoListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.Validations;
import com.lanchonete.domain.entities.produto.entities.Produto;
import com.lanchonete.domain.services.produto.ProdutoService;

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

    private final ProdutoService _service;

    @Autowired
    public ProdutoController(ProdutoService service) {
        _service = service;

    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new ProdutoDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<ProdutoListDto>> list(@RequestParam(name = "page") int page) {
        Page<ProdutoListDto> list = this._service.listDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("lis/active")
    public ResponseEntity<Page<ProdutoListDto>> listActive(@RequestParam(name = "page") int page) {
        Page<ProdutoListDto> list = this._service.listActiveDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("lis/desactive")
    public ResponseEntity<Page<ProdutoListDto>> listDesactive(@RequestParam(name = "page") int page) {
        Page<ProdutoListDto> list = this._service.listDesactiveDto(page);
        return ResponseEntity.ok(list);
    }


    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {
        Produto entity = this._service.find(id);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));
        return ResponseEntity.badRequest().body("");
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() ProdutoDto entityDto) {
        if (!Validations.by(entityDto).isValid())
            return ResponseEntity.badRequest().body(Validations.get().getErros());

        Produto entity = Mapper.map(entityDto);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body("");
        entity = this._service.save(entity);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, ProdutoDto.class));
        return ResponseEntity.badRequest().body("");
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody() ProdutoDto entityDto) {
        if (!Validations.by(entityDto).isValid())
            return ResponseEntity.badRequest().body(Validations.get().getErros());

        Produto entity = this._service.find(entityDto.id);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body("");

        entity = Mapper.map(entityDto, entity);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, ProdutoDto.class));
        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("active")
    public ResponseEntity<Object> active(@RequestParam(name = "id") long id) {
        Produto entity = this._service.find(id);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body("");

        entity.setAtivo(true);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, ProdutoDto.class));
        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("desactive")
    public ResponseEntity<Object> desactive(@RequestParam(name = "id") long id) {
        Produto entity = this._service.find(id);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body("");

        entity.setAtivo(false);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, ProdutoDto.class));
        return ResponseEntity.badRequest().body("");
    }

}