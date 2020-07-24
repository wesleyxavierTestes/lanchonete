package com.lanchonete.controllers;

import java.util.Objects;

import com.lanchonete.apllication.dto.venda.VendaDto;
import com.lanchonete.apllication.dto.venda.VendaListDto;
import com.lanchonete.domain.entities.venda.Venda;
import com.lanchonete.domain.services.venda.VendaService;
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
@RequestMapping("api/venda")
public class VendaController {
    
    private ModelMapper mapper = ModelMapperUtils.getInstance();

    private final VendaService _service;

    @Autowired
    public VendaController(VendaService service) {
        _service = service;
        mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE);
    }

    @GetMapping("list")
    public ResponseEntity<Page<VendaListDto>> list(@RequestParam(name = "page") int page) {
        Page<VendaListDto> list = this._service.listDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Venda> find(@RequestParam(name = "id") long id) {
        Venda entity = this._service.find(id);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(entity);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() VendaDto entityDto) {
        if (!entityDto.getIsValid())
            return ResponseEntity.badRequest().build();

        Venda entity = this._service.save(mapper.map(entityDto, Venda.class));
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(mapper.map(entity, VendaDto.class));
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody() VendaDto entityDto) {
        if (!entityDto.getIsValid())
            return ResponseEntity.badRequest().build();

        Venda entity = this._service.find(entityDto.id);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().build();
        
        entity = this._service.update(entityDto.updateEntity(entity));

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(mapper.map(entity, VendaDto.class));
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("active")
    public ResponseEntity<Object> active(@RequestParam(name = "id") long id) {
        Venda entity = this._service.find(id);
        
        entity.setAtivo(true);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(mapper.map(entity, VendaDto.class));
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("desactive")
    public ResponseEntity<Object> desactive(@RequestParam(name = "id") long id) {
        Venda entity = this._service.find(id);

        entity.setAtivo(false);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(mapper.map(entity, VendaDto.class));
        return ResponseEntity.badRequest().build();
    }
}