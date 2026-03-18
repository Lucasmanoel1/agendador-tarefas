package com.lucasmanoel.agendadortarefas.business;

import com.lucasmanoel.agendadortarefas.business.dto.TarefasDTO;
import com.lucasmanoel.agendadortarefas.business.mapper.TarefaUpdateConverter;
import com.lucasmanoel.agendadortarefas.business.mapper.TarefasConveter;
import com.lucasmanoel.agendadortarefas.infrastructure.ResourceNotFoudException;
import com.lucasmanoel.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.lucasmanoel.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.lucasmanoel.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.lucasmanoel.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConveter tarefaConveter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;

    public TarefasDTO gravaTarefa(String token, TarefasDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmail(email);
        TarefasEntity entity = tarefaConveter.paraTarefaEntity(dto);
        return tarefaConveter.paraTerefaDTO(tarefasRepository.save(entity));
    }

    public List<TarefasDTO> buscaListaTarefas(LocalDateTime dataInicial, LocalDateTime dataFinal){
        return tarefaConveter.paraListaTerefasDTO(tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefasDTO> busaListaTarefasEmail(String token){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return tarefaConveter.paraListaTerefasDTO(tarefasRepository.findByEmail(email));
    }

    public void deletaTarefaId(String id){
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoudException e) {
            throw new ResourceNotFoudException("Id não encontrado!"+ e.getCause());
        }
    }
    public TarefasDTO alteraStatus(StatusNotificacaoEnum status, String id){
        TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoudException("ID não encontrado")
        );
        entity.setStatusNotificacaoEnum(status);
        return tarefaConveter.paraTerefaDTO(tarefasRepository.save(entity));
    }
    public TarefasDTO updateTarefa(TarefasDTO dto, String id){
        TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoudException("ID não encontrado")
        );
        dto.setDataAlteracao(LocalDateTime.now());
        tarefaUpdateConverter.updateTarefas(dto, entity);
        return tarefaConveter.paraTerefaDTO(tarefasRepository.save(entity));
    }
}
