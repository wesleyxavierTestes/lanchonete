package com.lanchonete.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lanchonete.apllication.dto.cliente.EnderecoDto;
import com.lanchonete.utils.HttpBase;
import com.lanchonete.utils.UrlConstants;

// import com.lanchonete.apllication.dto.cliente.EnderecoDto;
// import com.lanchonete.utils.HttpBase;
// import com.lanchonete.utils.UrlConstants;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("api/cep")
public class CepController {
    
    @GetMapping(value="path")
    public ResponseEntity<EnderecoDto> getMethodName(@RequestParam String cep) {
        try {
            HttpBase.HttpGet(UrlConstants.getViaCep(cep), EnderecoDto.class);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return new ResponseEntity<>(new EnderecoDto(), HttpStatus.BAD_REQUEST);
        }
    }
}