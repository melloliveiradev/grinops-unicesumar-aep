package com.aep.grinops.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Document (collection = "metricas_consumo")
public class MetricaConsumo {
    
    @Id 
    private String id;
    private String servidorId;
    private LocalDateTime dataHora;
    private double usoCpuPercentual;
    private double usoRamPercentual;
    private double consumoEnergiaKwh;
    private double estimativaCo2Gramas;
}
