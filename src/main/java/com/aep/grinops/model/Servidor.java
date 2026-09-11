package com.aep.grinops.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Document (collection = "servidores")
public class Servidor {

    @Id 
    private String id;
    private String nomeHost;
    private String provedorDaNuvem;
    private boolean ocioso;

    private ConfiguracaoHardware configuracaoHardware;
    
    private List<Container> containers;
}
