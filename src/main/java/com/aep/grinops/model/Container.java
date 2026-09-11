package com.aep.grinops.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Container {
    
    private String nome;
    private String imagem;
    private boolean ativo;
}
