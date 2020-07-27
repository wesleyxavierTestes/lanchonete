package com.lanchonete.controllers;

import java.util.Objects;

import javax.validation.Valid;

import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.dto.produto.ProdutoListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.produto.entities.Produto;
import com.lanchonete.domain.services.produto.ProdutoService;
import com.lanchonete.utils.MessageError;

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
public class ProdutoController extends AbstractBaseController {

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

    @GetMapping("list/active")
    public ResponseEntity<Page<ProdutoListDto>> listActive(@RequestParam(name = "page") int page) {
        Page<ProdutoListDto> list = this._service.listActiveDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("list/desactive")
    public ResponseEntity<Page<ProdutoListDto>> listDesactive(@RequestParam(name = "page") int page) {
        Page<ProdutoListDto> list = this._service.listDesactiveDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {
        Produto entity = this._service.find(id);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() @Valid ProdutoDto entityDto) {

        Produto entity = this._service.save(Mapper.map(entityDto));

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.ERROS_DATABASE);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody() @Valid ProdutoDto entityDto) {

        Produto entity = this._service.find(entityDto.id);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        this._service.update(Mapper.map(entityDto, entity));

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.ERROS_DATABASE);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("active")
    public ResponseEntity<Object> active(@RequestParam(name = "id") long id) {
        Produto entity = this._service.find(id);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        entity.setAtivo(true);
        this._service.update(entity);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.ERROS_DATABASE);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("desactive")
    public ResponseEntity<Object> desactive(@RequestParam(name = "id") long id) {
        Produto entity = this._service.find(id);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        entity.setAtivo(false);
        this._service.update(entity);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.ERROS_DATABASE);

        return ResponseEntity.ok(Mapper.map(entity));
    }

}