package com.lanchonete.controllers;

import java.time.LocalDateTime;

import javax.validation.Valid;

import com.lanchonete.apllication.dto.venda.VendaDto;
import com.lanchonete.apllication.dto.venda.VendaListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.venda.Venda;
import com.lanchonete.domain.services.venda.VendaService;

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
@RequestMapping("api/venda")
public class VendaController extends AbstractBaseController {

    private final VendaService _service;

    @Autowired
    public VendaController(VendaService service) {
        _service = service;
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new VendaDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<VendaListDto>> list(@RequestParam(name = "page") int page) {

        Page<VendaListDto> list = this._service.listDto(page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("list/cancel")
    public ResponseEntity<Page<VendaListDto>> listCancel(@RequestParam(name = "page") int page) {

        Page<VendaListDto> list = this._service.listCancelDto(page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {

        Venda entity = this._service.find(id);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() @Valid VendaDto entityDto) {

        Venda entity = this._service.save(Mapper.map(entityDto));

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("cancel")
    public ResponseEntity<Object> cancel(@RequestParam(name = "id") long id) {

        Venda entity = this._service.find(id);

        entity.setAtivo(true);
        entity.setCancelado(true);
        entity.setDataCancelado(LocalDateTime.now());

        this._service.update(entity);

        return ResponseEntity.ok(Mapper.map(entity));
    }
}