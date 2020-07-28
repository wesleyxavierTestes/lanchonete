package com.lanchonete.controllers;

import java.util.Objects;

import javax.validation.Valid;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.categoria.CategoriaListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.services.categoria.CategoriaService;
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
@RequestMapping("api/categoria")
public class CategoriaController extends AbstractBaseController {

    private final CategoriaService _service;

    @Autowired
    public CategoriaController(CategoriaService service) {
        _service = service;
    }

    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new CategoriaDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<CategoriaListDto>> list(@RequestParam(name = "page") int page) {
        Page<CategoriaListDto> list = this._service.listDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("list/name")
    public ResponseEntity<Page<CategoriaListDto>> listFilter(
        @RequestParam(name = "page") int page,   
        @RequestBody CategoriaDto filter) {
        Page<CategoriaListDto> list = this._service.listFilterDto(Mapper.map(filter), page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("list/active")
    public ResponseEntity<Page<CategoriaListDto>> listActive(@RequestParam(name = "page") int page) {
        Page<CategoriaListDto> list = this._service.listActiveDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("list/desactive")
    public ResponseEntity<Page<CategoriaListDto>> listDesactive(@RequestParam(name = "page") int page) {
        Page<CategoriaListDto> list = this._service.listDesactiveDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {
        
        Categoria entity = this._service.find(id);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() @Valid CategoriaDto entityDto) {
        
        Categoria entity = this._service.save(Mapper.map(entityDto));
        
        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody() @Valid CategoriaDto entityDto) {
        Categoria entity = this._service.find(entityDto.id);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        this._service.update(Mapper.map(entityDto, entity));

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> delete(@RequestParam(name = "id") long id) {
        
        Categoria entity = this._service.delete(id);

        return ResponseEntity.ok(Mapper.map(entity));
    }
}