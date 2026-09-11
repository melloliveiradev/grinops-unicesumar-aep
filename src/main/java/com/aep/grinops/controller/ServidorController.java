package com.aep.grinops.controller;

import com.aep.grinops.model.Servidor;
import com.aep.grinops.repository.ServidorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servidores")
public class ServidorController {

    private final ServidorRepository servidorRepository;

    public ServidorController(ServidorRepository servidorRepository) {
        this.servidorRepository = servidorRepository;
    }

    @PostMapping
    public ResponseEntity<Servidor> criar(@RequestBody Servidor servidor) {
        Servidor novoServidor = servidorRepository.save(servidor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoServidor);
    }

    @GetMapping
    public ResponseEntity<List<Servidor>> listarTodos() {
        return ResponseEntity.ok(servidorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servidor> buscarPorId(@PathVariable String id) {
        return servidorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}