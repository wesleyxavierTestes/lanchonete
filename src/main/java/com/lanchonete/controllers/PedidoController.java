package com.lanchonete.controllers;

import java.util.Objects;

import com.lanchonete.apllication.dto.pedido.PedidoDto;
import com.lanchonete.apllication.dto.pedido.PedidoListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.Validations;
import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.domain.services.pedido.PedidoService;
import com.lanchonete.utils.MessageError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pedido")
public class PedidoController {
    private final PedidoService _service;

    @Autowired
    private Validations validations;

    @Autowired
    public PedidoController(PedidoService service) {
        _service = service;
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new PedidoDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<PedidoListDto>> list(@RequestParam(name = "page") int page) {
        Page<PedidoListDto> list = this._service.listDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {
        Pedido entity = this._service.find(id);
        
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));

        return ResponseEntity.badRequest().body("");
    }

    @GetMapping("lis/cancel")
    public ResponseEntity<Page<PedidoListDto>> listActive(@RequestParam(name = "page") int page) {
        Page<PedidoListDto> list = this._service.listCancelDto(page);
        return ResponseEntity.ok(list);
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() PedidoDto entityDto) {
        if (!validations.by(entityDto).isValid())
            return ResponseEntity.badRequest().body(validations.getErros());

        Pedido entity = Mapper.map(entityDto);
        if (!Objects.nonNull(entity)) 
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        entity = this._service.save(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));

        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("cancel")
    public ResponseEntity<Object> cancel(@RequestParam(name = "id") long id) {
        Pedido entity = this._service.find(id);

        if (!Objects.nonNull(entity)) 
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        entity.setAtivo(true);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));

        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("desactive")
    public ResponseEntity<Object> desactive(@RequestParam(name = "id") long id) {
        Pedido entity = this._service.find(id);

        if (!Objects.nonNull(entity)) 
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        entity.setAtivo(false);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));

        return ResponseEntity.badRequest().body("");
    }
}