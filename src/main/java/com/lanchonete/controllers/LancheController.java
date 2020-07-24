package com.lanchonete.controllers;

import java.util.Objects;

import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.apllication.dto.lanche.LancheListDto;
import com.lanchonete.domain.entities.cardapio.lanche.Lanche;
import com.lanchonete.domain.services.lanche.LancheService;
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
@RequestMapping("api/lanche")
public class LancheController {
    
    private ModelMapper mapper = ModelMapperUtils.getInstance();

    private final LancheService _service;

    @Autowired
    public LancheController(LancheService service) {
        _service = service;
        mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE);
    }

    @GetMapping("list")
    public ResponseEntity<Page<LancheListDto>> list(@RequestParam(name = "page") int page) {
        Page<LancheListDto> list = this._service.listDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Lanche> find(@RequestParam(name = "id") long id) {
        Lanche entity = this._service.find(id);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(entity);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() LancheDto entityDto) {
        if (!entityDto.getIsValid())
            return ResponseEntity.badRequest().build();

        Lanche entity = this._service.save(mapper.map(entityDto, Lanche.class));
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(mapper.map(entity, LancheDto.class));
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody() LancheDto entityDto) {
        if (!entityDto.getIsValid())
            return ResponseEntity.badRequest().build();

        Lanche entity = this._service.find(entityDto.id);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().build();
        
        entity = this._service.update(entityDto.updateEntity(entity));

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(mapper.map(entity, LancheDto.class));
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("active")
    public ResponseEntity<Object> active(@RequestParam(name = "id") long id) {
        Lanche entity = this._service.find(id);
        
        entity.setAtivo(true);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(mapper.map(entity, LancheDto.class));
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("desactive")
    public ResponseEntity<Object> desactive(@RequestParam(name = "id") long id) {
        Lanche entity = this._service.find(id);

        entity.setAtivo(false);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(mapper.map(entity, LancheDto.class));
        return ResponseEntity.badRequest().build();
    }

}