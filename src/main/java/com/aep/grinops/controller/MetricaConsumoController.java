package com.aep.grinops.controller;

import com.aep.grinops.model.MetricaConsumo;
import com.aep.grinops.service.AnaliseConsumoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metricas")
public class MetricaConsumoController {

    private final AnaliseConsumoService analiseConsumoService;

    public MetricaConsumoController(AnaliseConsumoService analiseConsumoService) {
        this.analiseConsumoService = analiseConsumoService;
    }

    @PostMapping
    public ResponseEntity<MetricaConsumo> registrarMetrica(
            @RequestBody MetricaConsumo metrica,
            @RequestParam(defaultValue = "sa-east-1") String regiaoNuvem) {
        
        MetricaConsumo metricaProcessada = analiseConsumoService.registrarMetrica(metrica, regiaoNuvem);
        return ResponseEntity.status(HttpStatus.CREATED).body(metricaProcessada);
    }
}