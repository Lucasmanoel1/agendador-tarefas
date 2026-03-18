package com.lucasmanoel.agendadortarefas.business.mapper;

import com.lucasmanoel.agendadortarefas.business.dto.TarefasDTO;
import com.lucasmanoel.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConveter {

    TarefasEntity paraTarefaEntity(TarefasDTO dto);

    TarefasDTO paraTerefaDTO(TarefasEntity entity);

    List<TarefasEntity> paraListaTerefasEntity(List<TarefasDTO> dto);

    List<TarefasDTO> paraListaTerefasDTO(List<TarefasEntity> entity);


}
