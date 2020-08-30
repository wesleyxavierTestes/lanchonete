package com.lanchonete.domain.services.usuario;

import com.lanchonete.domain.entities.user.Usuario;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.usuario.IUsuarioRepository;

import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends BaseService<Usuario, IUsuarioRepository> {

    public UsuarioService(IUsuarioRepository repository) {
        super(repository);
    }
    
}