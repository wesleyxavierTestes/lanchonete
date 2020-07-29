package com.lanchonete.controllers;

import java.util.Objects;

import javax.validation.Valid;

import com.lanchonete.apllication.dto.combo.ComboDto;
import com.lanchonete.apllication.dto.combo.ComboListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cardapio.combo.Combo;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.services.categoria.CategoriaService;
import com.lanchonete.domain.services.combo.ComboService;
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
@RequestMapping("api/combo")
public class ComboController extends AbstractBaseController {

    private final ComboService _service;
    private final CategoriaService _serviceCategoria;

    @Autowired
    public ComboController(ComboService service, CategoriaService serviceCategoria) {
        _service = service;
        _serviceCategoria = serviceCategoria;
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new ComboDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<ComboListDto>> list(@RequestParam(name = "page") int page) {
        Page<ComboListDto> list = this._service.listDto(page);
        return ResponseEntity.ok(list);
    }

    @PostMapping("list/filter")
    public ResponseEntity<Page<ComboListDto>> listFilter(@RequestParam(name = "page") int page,
            @RequestBody ComboDto filter) {
        Page<ComboListDto> list = this._service.listFilterDto(Mapper.map(filter), page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("list/active")
    public ResponseEntity<Page<ComboListDto>> listActive(@RequestParam(name = "page") int page) {
        Page<ComboListDto> list = this._service.listActiveDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("list/desactive")
    public ResponseEntity<Page<ComboListDto>> listDesactive(@RequestParam(name = "page") int page) {
        Page<ComboListDto> list = this._service.listDesactiveDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {

        Combo entity = this._service.find(id);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() @Valid ComboDto entityDto) {

        Categoria categoria = this._serviceCategoria.find(entityDto.categoria.id);

        if (!Objects.nonNull(categoria))
            return ResponseEntity.badRequest().body("Categoria" + MessageError.IS_MANDATORY);

        Combo entity = Mapper.map(entityDto);

        this._service.criarCombo(entity, categoria);

        try {
            entity = this._service.save(entity);
        } catch (Exception e) {
            System.out.println(e);
        }

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("active")
    public ResponseEntity<Object> active(@RequestParam(name = "id") long id) {

        Combo entity = this._service.ative(id, true);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("desactive")
    public ResponseEntity<Object> desactive(@RequestParam(name = "id") long id) {

        Combo entity = this._service.ative(id, false);

        return ResponseEntity.ok(Mapper.map(entity));
    }
}