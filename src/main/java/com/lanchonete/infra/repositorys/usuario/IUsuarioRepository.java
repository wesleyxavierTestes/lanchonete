package com.lanchonete.infra.repositorys.usuario;

import com.lanchonete.domain.entities.user.Usuario;
import com.lanchonete.infra.repositorys.IBaseRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository  extends IBaseRepository<Usuario>  {
    
    Usuario findByUsername(String username);
}