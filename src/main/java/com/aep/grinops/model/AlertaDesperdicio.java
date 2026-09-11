package com.aep.grinops.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Document(collection = "alertas_desperdicio")
public class AlertaDesperdicio {

    @Id 
    private String id;
    private String servidorId;
    
    // Inicializa a data automaticamente com o momento atual se ela vier nula
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    private String status;
    private double custoEstimadoDesperdicado;

    private List<Tratativa> historicoTratativas = new ArrayList<>();
}