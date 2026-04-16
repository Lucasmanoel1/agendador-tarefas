package com.lucasmanoel.agendadortarefas.business;

import com.lucasmanoel.agendadortarefas.business.dto.TarefasDTORecords;
import com.lucasmanoel.agendadortarefas.business.mapper.TarefaUpdateConverter;
import com.lucasmanoel.agendadortarefas.business.mapper.TarefasConveter;
import com.lucasmanoel.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
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

    public TarefasDTORecords gravaTarefa(String token, TarefasDTORecords dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        TarefasDTORecords dtofinal = new TarefasDTORecords(null, dto.nome(),
                dto.descricao(), LocalDateTime.now(), dto.dataEvento(), email, null,
                StatusNotificacaoEnum.PENDENTE);
        TarefasEntity entity = tarefaConveter.paraTarefaEntity(dtofinal);
        return tarefaConveter.paraTerefaDTO(tarefasRepository.save(entity));
    }

    public List<TarefasDTORecords> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal, StatusNotificacaoEnum status){
        return tarefaConveter.paraListaTerefasDTO(tarefasRepository.findByDataEventoBetweenAndStatusNotificacaoEnum(dataInicial, dataFinal, StatusNotificacaoEnum.PENDENTE));
    }

    public List<TarefasDTORecords> busaListaTarefasEmail(String token){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return tarefaConveter.paraListaTerefasDTO(tarefasRepository.findByEmail(email));
    }

    public void deletaTarefaId(String id){
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Id não encontrado!"+ e.getCause());
        }
    }
    public TarefasDTORecords alteraStatus(StatusNotificacaoEnum status, String id){
        TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ID não encontrado")
        );
        entity.setStatusNotificacaoEnum(status);
        return tarefaConveter.paraTerefaDTO(tarefasRepository.save(entity));
    }
    public TarefasDTORecords updateTarefa(TarefasDTORecords dto, String id){
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("ID não encontrado")
            );
            tarefaUpdateConverter.updateTarefas(dto, entity);
            return tarefaConveter.paraTerefaDTO(tarefasRepository.save(entity));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa");
        }
    }
}
