package com.lanchonete.controllers;

import java.util.Objects;
import java.util.UUID;

import javax.validation.Valid;

import com.lanchonete.apllication.dto.cardapio.CardapioDto;
import com.lanchonete.apllication.dto.cardapio.CardapioItemDto;
import com.lanchonete.apllication.dto.cardapio.CardapioListDto;
import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.entities.outros.Outros;
import com.lanchonete.domain.services.cardapio.CardapioService;
import com.lanchonete.utils.MessageError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("api/cardapio")
@Api(value = "Cardapio")
public class CardapioController extends AbstractBaseController {

    private final CardapioService _service;

    @Autowired
    public CardapioController(CardapioService service) {
        _service = service;
    }

    @ApiOperation(value = "Novo")
    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new CardapioDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<CardapioListDto>> list(@RequestParam(name = "page") int page) {

        Page<CardapioListDto> list = this._service.listDto(page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("list/full")
    public ResponseEntity<Page<CardapioListDto>> listFull(@RequestParam(name = "page") int page) {
        Page<CardapioListDto> list = this._service.listDtoFull(page);
        return ResponseEntity.ok(list);
    }

    @PostMapping("list/filter")
    public ResponseEntity<Page<CardapioListDto>> listFilter(@RequestParam(name = "page") int page,
            @RequestBody CardapioDto filter) {

        Page<CardapioListDto> list = this._service.listFilterDto(Mapper.map(filter), page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("list/active")
    public ResponseEntity<Page<CardapioListDto>> listActive(@RequestParam(name = "page") int page) {

        Page<CardapioListDto> list = this._service.listActiveDto(page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("list/desactive")
    public ResponseEntity<Page<CardapioListDto>> listDesactive(@RequestParam(name = "page") int page) {
        
        Page<CardapioListDto> list = this._service.listDesactiveDto(page);
        
        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {

        Cardapio entity = this._service.find(id);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() @Valid CardapioDto entityDto) {

        Cardapio entity = Mapper.map(entityDto);

        this._service.criarCardapio(entity);

        try {
            this._service.save(entity);
        } catch (Exception e) {
            // log ex.getMessage()
            throw new RegraNegocioException("Cardápio inválido");
        }

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody() @Valid CardapioDto entityDto) {

        Cardapio entity = this._service.find(entityDto.id);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        this._service.update(Mapper.map(entityDto, entity));

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PutMapping("additem")
    public ResponseEntity<Object> addItem(@RequestParam(name = "id") long id,
    @RequestBody() @Valid CardapioItemDto entityDto) {

        Cardapio entity = this._service.find(id);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        Outros map = Mapper.map(entityDto, Outros.class);
        map.setCodigo(UUID.fromString(entityDto.codigo));
        this._service.criarCardapio(entity, map);
        
        this._service.update(entity);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("active")
    public ResponseEntity<Object> active(@RequestParam(name = "id") long id) {

        Cardapio entity = this._service.ative(id, true);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("desactive")
    public ResponseEntity<Object> desactive(@RequestParam(name = "id") long id) {

        Cardapio entity = this._service.ative(id, false);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> delete(@RequestParam(name = "id") long id) {

        Cardapio entity = this._service.delete(id);

        return ResponseEntity.ok(Mapper.map(entity));
    }

}