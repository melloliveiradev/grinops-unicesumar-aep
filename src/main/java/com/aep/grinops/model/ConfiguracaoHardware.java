package com.aep.grinops.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracaoHardware {
    
    @JsonProperty("vCpus")
    private int vCpus;
    private double ramGb;
    private double tdpWatts;
    private String regiaoNuvem; //Ex: "us-east-1", "sa-east-1"
}
