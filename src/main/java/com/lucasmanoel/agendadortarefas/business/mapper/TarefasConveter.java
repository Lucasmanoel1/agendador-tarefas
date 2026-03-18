package com.lucasmanoel.agendadortarefas.business.mapper;

import com.lucasmanoel.agendadortarefas.business.dto.TarefasDTO;
import com.lucasmanoel.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefasConveter {

    TarefasEntity paraTarefaEntity(TarefasDTO dto);

    TarefasDTO paraTerefaDTO(TarefasEntity entity);
}
