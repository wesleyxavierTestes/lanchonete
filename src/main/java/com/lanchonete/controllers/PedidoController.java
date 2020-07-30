package com.lanchonete.controllers;

import java.time.LocalDateTime;

import javax.validation.Valid;

import com.lanchonete.apllication.dto.pedido.PedidoDto;
import com.lanchonete.apllication.dto.pedido.PedidoListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.domain.services.cardapio.CardapioService;
import com.lanchonete.domain.services.pedido.PedidoService;

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
public class PedidoController extends AbstractBaseController {

    private final PedidoService _service;
    private final CardapioService _cardapioService;

    @Autowired
    public PedidoController(PedidoService service, CardapioService cardapioService) {
        _service = service;
        _cardapioService = cardapioService;
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

    @GetMapping("list/day")
    public ResponseEntity<Page<PedidoListDto>> listDay(@RequestParam(name = "page") int page) {

        Page<PedidoListDto> list = this._service.listDay(page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("list/client")
    public ResponseEntity<Page<PedidoListDto>> listClient(@RequestParam(name = "page") int page,
            @RequestParam(name = "id") long id) {

        Page<PedidoListDto> list = this._service.listClient(id, page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("list/cancel")
    public ResponseEntity<Page<PedidoListDto>> listActive(@RequestParam(name = "page") int page) {

        Page<PedidoListDto> list = this._service.listCancelDto(page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {

        Pedido entity = this._service.find(id);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() @Valid PedidoDto entityDto) {

        Cardapio cardapio = _cardapioService.cardapioActive();

        Pedido entity = Mapper.map(entityDto);

        this._service.criarPedido(entity, cardapio);

        // this._service.save(entity);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("cancel")
    public ResponseEntity<Object> cancel(@RequestParam(name = "id") long id) {

        Pedido entity = this._service.find(id);

        entity.setAtivo(false);
        entity.setCancelado(true);
        entity.setDataCancelado(LocalDateTime.now());

        this._service.update(entity);

        return ResponseEntity.ok(Mapper.map(entity));
    }
}