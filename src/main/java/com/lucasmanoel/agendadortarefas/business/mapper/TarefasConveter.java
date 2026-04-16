package com.lucasmanoel.agendadortarefas.business.mapper;

import com.lucasmanoel.agendadortarefas.business.dto.TarefasDTORecords;
import com.lucasmanoel.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConveter {

    TarefasEntity paraTarefaEntity(TarefasDTORecords dto);

    TarefasDTORecords paraTerefaDTO(TarefasEntity entity);

    List<TarefasEntity> paraListaTerefasEntity(List<TarefasDTORecords> dto);

    List<TarefasDTORecords> paraListaTerefasDTO(List<TarefasEntity> entity);


}
