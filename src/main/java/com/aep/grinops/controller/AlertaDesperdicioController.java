package com.aep.grinops.controller;

import com.aep.grinops.model.AlertaDesperdicio;
import com.aep.grinops.model.Tratativa;
import com.aep.grinops.repository.AlertaDesperdicioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alertas")
public class AlertaDesperdicioController {

    private final AlertaDesperdicioRepository alertaDesperdicioRepository;

    public AlertaDesperdicioController(AlertaDesperdicioRepository alertaDesperdicioRepository) {
        this.alertaDesperdicioRepository = alertaDesperdicioRepository;
    }

    // 1. LISTAR TODOS (GET)
    @GetMapping
    public ResponseEntity<List<AlertaDesperdicio>> listarTodos() {
        return ResponseEntity.ok(alertaDesperdicioRepository.findAll());
    }

    // 2. BUSCAR POR ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<AlertaDesperdicio> buscarPorId(@PathVariable String id) {
        return alertaDesperdicioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. CRIAR (POST)
    @PostMapping
    public ResponseEntity<AlertaDesperdicio> criar(@RequestBody AlertaDesperdicio alerta) {
        AlertaDesperdicio novoAlerta = alertaDesperdicioRepository.save(alerta);
        return ResponseEntity.ok(novoAlerta);
    }

    // 4. ATUALIZAR (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<AlertaDesperdicio> atualizar(@PathVariable String id, @RequestBody AlertaDesperdicio alertaAtualizado) {
        Optional<AlertaDesperdicio> alertaExistente = alertaDesperdicioRepository.findById(id);

        if (alertaExistente.isPresent()) {
            AlertaDesperdicio alerta = alertaExistente.get();
            alerta.setServidorId(alertaAtualizado.getServidorId());
            alerta.setStatus(alertaAtualizado.getStatus());
            alerta.setCustoEstimadoDesperdicado(alertaAtualizado.getCustoEstimadoDesperdicado());
            alerta.setHistoricoTratativas(alertaAtualizado.getHistoricoTratativas());
            
            AlertaDesperdicio salvo = alertaDesperdicioRepository.save(alerta);
            return ResponseEntity.ok(salvo);
        }

        return ResponseEntity.notFound().build();
    }

    // 5. ADICIONAR TRATATIVA (POST)
    @PostMapping("/{id}/tratativas")
    public ResponseEntity<AlertaDesperdicio> adicionarTratativa(
            @PathVariable String id, 
            @RequestBody Tratativa tratativa) {
        
        Optional<AlertaDesperdicio> alertaOpt = alertaDesperdicioRepository.findById(id);
        if (alertaOpt.isPresent()) {
            AlertaDesperdicio alerta = alertaOpt.get();
            
            if (alerta.getHistoricoTratativas() == null) {
                alerta.setHistoricoTratativas(new ArrayList<>());
            }
            
            if (tratativa.getDataHora() == null) {
                tratativa.setDataHora(LocalDateTime.now());
            }
            
            alerta.getHistoricoTratativas().add(tratativa);
            AlertaDesperdicio salvo = alertaDesperdicioRepository.save(alerta);
            return ResponseEntity.ok(salvo);
        }
        
        return ResponseEntity.notFound().build();
    }

    // 6. DELETAR (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        if (alertaDesperdicioRepository.existsById(id)) {
            alertaDesperdicioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}