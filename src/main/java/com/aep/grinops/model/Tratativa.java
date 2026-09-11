package com.aep.grinops.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Tratativa {
    private LocalDateTime dataHora;
    private String acaoTomada;
    private String responsavel;
}
