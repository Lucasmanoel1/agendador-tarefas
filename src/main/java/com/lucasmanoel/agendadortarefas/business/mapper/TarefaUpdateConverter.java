package com.lucasmanoel.agendadortarefas.business.mapper;

import com.lucasmanoel.agendadortarefas.business.dto.TarefasDTORecords;
import com.lucasmanoel.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefaUpdateConverter {
    void updateTarefas(TarefasDTORecords dto, @MappingTarget TarefasEntity entity);
}
