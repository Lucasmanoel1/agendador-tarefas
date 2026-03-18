package com.lucasmanoel.agendadortarefas.controller;

import com.lucasmanoel.agendadortarefas.business.TarefasService;
import com.lucasmanoel.agendadortarefas.business.dto.TarefasDTO;
import com.lucasmanoel.agendadortarefas.infrastructure.ResourceNotFoudException;
import com.lucasmanoel.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDTO> gravarTarefas(@RequestBody TarefasDTO dto,
                                                    @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(tarefasService.gravaTarefa(token, dto));
    }
    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDTO>> buscaListaTarefasEventos(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
                                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal){
        return ResponseEntity.ok(tarefasService.buscaListaTarefas(dataInicial, dataFinal));
    }
    @GetMapping
    public ResponseEntity<List<TarefasDTO>> buscaListaTarefasEmail(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(tarefasService.busaListaTarefasEmail(token));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletaTarefaId(@RequestParam String id){
        tarefasService.deletaTarefaId(id);
        return ResponseEntity.ok().build();
    }
    @PatchMapping
    public  ResponseEntity<TarefasDTO> alteraStatusTarefa(@RequestParam StatusNotificacaoEnum status,
                                                          @RequestParam String id){
        return ResponseEntity.ok(tarefasService.alteraStatus(status, id));
    }
    @PutMapping
    public ResponseEntity<TarefasDTO> updateTarefas(@RequestBody TarefasDTO dto,
                                                    @RequestParam String id){
        return ResponseEntity.ok(tarefasService.updateTarefa(dto, id));
    }
}
