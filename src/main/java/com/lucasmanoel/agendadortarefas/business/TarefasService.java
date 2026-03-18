package com.lucasmanoel.agendadortarefas.business;

import com.lucasmanoel.agendadortarefas.business.dto.TarefasDTO;
import com.lucasmanoel.agendadortarefas.business.mapper.TarefasConveter;
import com.lucasmanoel.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.lucasmanoel.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.lucasmanoel.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.lucasmanoel.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConveter tarefaConveter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravaTarefa(String token, TarefasDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmail(email);
        TarefasEntity entity = tarefaConveter.paraTarefaEntity(dto);
        return tarefaConveter.paraTerefaDTO(tarefasRepository.save(entity));
    }
}
