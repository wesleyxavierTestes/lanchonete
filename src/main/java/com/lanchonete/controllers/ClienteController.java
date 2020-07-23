package com.lanchonete.controllers;

import com.lanchonete.domain.entities.cliente.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    

    @GetMapping("listar")
    public RequestEntity<Page<Object>> listar() {

        return null;
    }
}