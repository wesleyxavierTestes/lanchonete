package com.lanchonete.domain.services.usuario;

import java.util.List;
import java.util.Optional;

import com.lanchonete.domain.entities.user.Usuario;
import com.lanchonete.infra.repositorys.usuario.IUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final IUsuarioRepository _usuarioRepository;

    @Autowired
    public CustomUserDetailsService(IUsuarioRepository usuarioRepository) {
        _usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Usuario user =  Optional.ofNullable(_usuarioRepository.findByUsername(username))
        .orElseThrow(() -> new UsernameNotFoundException("usuário não encontrado"));
        
        List<GrantedAuthority> authorityADMIN = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
        List<GrantedAuthority> authorityUSER = AuthorityUtils.createAuthorityList("ROLE_USER");


        return new User(user.getName(), user.getPassword(), user.isAdmin() ? authorityADMIN : authorityUSER);
    }    
}