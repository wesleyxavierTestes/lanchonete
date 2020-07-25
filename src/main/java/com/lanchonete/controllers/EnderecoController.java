package com.lanchonete.controllers;

import java.util.Objects;

import com.lanchonete.apllication.dto.cliente.EnderecoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cliente.Endereco;
import com.lanchonete.domain.services.cliente.EnderecoService;

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
@RequestMapping("/api/endereco")
public class EnderecoController {

    private final EnderecoService _service;

    @Autowired
    public EnderecoController(EnderecoService service) {
        _service = service;

    }

    @GetMapping("list")
    public ResponseEntity<Page<Endereco>> listar(@RequestParam(name = "page") int page) {
        return ResponseEntity.ok(this._service.list(page));
    }

    @GetMapping("find")
    public ResponseEntity<Endereco> find(@RequestParam(name = "id") long id) {
        Endereco entity = this._service.find(id);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(entity);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() EnderecoDto entityDto) {

        Endereco entity = Mapper.map(entityDto);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().build();
        entity = this._service.save(entity);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, EnderecoDto.class));
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody() EnderecoDto entityDto) {

        Endereco entity = this._service.find(entityDto.id);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().build();

        entity = Mapper.map(entityDto, entity);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, EnderecoDto.class));
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> delete(@RequestParam(name = "id") long id) {
        Endereco entity = this._service.delete(id);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, EnderecoDto.class));
        return ResponseEntity.badRequest().build();
    }
}