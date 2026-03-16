package com.lucasmanoel.agendadortarefas.infrastructure.security;


import com.lucasmanoel.agendadortarefas.business.dto.UsuarioDTO;
import com.lucasmanoel.agendadortarefas.infrastructure.client.UsuarioClient;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl{

    @Autowired
    private UsuarioClient client;

    // Implementação do metodo para carregar detalhes do usuário pelo e-mail

    public UserDetails carregaDadosUsuario(String email, String token){
        UsuarioDTO usuarioDTO = client.buscaUsuarioPorEmail(email, token);
        return User
                .withUsername(usuarioDTO.getEmail())
                .password(usuarioDTO.getSenha())
                .build();
    }
}
