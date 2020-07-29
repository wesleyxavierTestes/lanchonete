package com.lanchonete.controllers;

import java.util.Objects;
import java.util.UUID;

import javax.validation.Valid;

import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.apllication.dto.lanche.LancheListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cardapio.lanche.Lanche;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.services.categoria.CategoriaService;
import com.lanchonete.domain.services.lanche.LancheService;
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
@RequestMapping("api/lanche")
public class LancheController extends AbstractBaseController {

    private final LancheService _service;
    private final CategoriaService _serviceCategoria;

    @Autowired
    public LancheController(LancheService service, CategoriaService serviceCategoria) {
        _service = service;
        _serviceCategoria = serviceCategoria;
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new LancheDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<LancheListDto>> list(@RequestParam(name = "page") int page) {
        
        Page<LancheListDto> list = this._service.listDto(page);
        
        return ResponseEntity.ok(list);
    }

    @PostMapping("list/filter")
    public ResponseEntity<Page<LancheListDto>> listFilter(
        @RequestParam(name = "page") int page,   
        @RequestBody LancheDto filter) {

        Page<LancheListDto> list = this._service.listFilterDto(Mapper.map(filter), page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("list/active")
    public ResponseEntity<Page<LancheListDto>> listActive(@RequestParam(name = "page") int page) {
        
        Page<LancheListDto> list = this._service.listActiveDto(page);
        
        return ResponseEntity.ok(list);
    }

    @GetMapping("list/desactive")
    public ResponseEntity<Page<LancheListDto>> listDesactive(@RequestParam(name = "page") int page) {
        
        Page<LancheListDto> list = this._service.listDesactiveDto(page);
        
        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {
        
        Lanche entity = this._service.find(id);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() @Valid LancheDto entityDto) {

        Categoria categoria = this._serviceCategoria.find(entityDto.categoria.id);
        
        if (!Objects.nonNull(categoria))
            return ResponseEntity.badRequest().body("Categoria" + MessageError.IS_MANDATORY);

        Lanche entity = Mapper.map(entityDto);
        
        this._service.criarLanche(entity, categoria);

        // fabrica lanche

        this._service.save(entity);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("active")
    public ResponseEntity<Object> active(@RequestParam(name = "id") long id) {
        
        Lanche entity = this._service.ative(id, true);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("desactive")
    public ResponseEntity<Object> desactive(@RequestParam(name = "id") long id) {
        
        Lanche entity = this._service.ative(id, false);

        return ResponseEntity.ok(Mapper.map(entity));
    }
}