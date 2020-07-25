package com.lanchonete.controllers;

import java.util.Objects;

import com.lanchonete.apllication.dto.cardapio.CardapioDto;
import com.lanchonete.apllication.dto.cardapio.CardapioListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.services.cardapio.CardapioService;

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
@RequestMapping("api/cardapio")
public class CardapioController {

    private final CardapioService _service;

    @Autowired
    public CardapioController(CardapioService service) {
        _service = service;
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new CardapioDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<CardapioListDto>> list(@RequestParam(name = "page") int page) {
        Page<CardapioListDto> list = this._service.listDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {
        Cardapio entity = this._service.find(id);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));
        return ResponseEntity.badRequest().body("");
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() CardapioDto entityDto) {

        Cardapio entity = Mapper.map(entityDto);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body("");
        entity = this._service.save(entity);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, CardapioDto.class));
        return ResponseEntity.badRequest().body("");
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody() CardapioDto entityDto) {

        Cardapio entity = this._service.find(entityDto.id);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body("");

        entity = Mapper.map(entityDto, entity);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, CardapioDto.class));
        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("active")
    public ResponseEntity<Object> active(@RequestParam(name = "id") long id) {
        Cardapio entity = this._service.find(id);

        entity.setAtivo(true);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, CardapioDto.class));
        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("desactive")
    public ResponseEntity<Object> desactive(@RequestParam(name = "id") long id) {
        Cardapio entity = this._service.find(id);

        entity.setAtivo(false);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, CardapioDto.class));
        return ResponseEntity.badRequest().body("");
    }

}