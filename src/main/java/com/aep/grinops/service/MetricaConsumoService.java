package com.aep.grinops.service;

import com.aep.grinops.model.AlertaDesperdicio;
import com.aep.grinops.model.MetricaConsumo;
import com.aep.grinops.repository.AlertaDesperdicioRepository;
import com.aep.grinops.repository.MetricaConsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricaConsumoService {

    @Autowired
    private MetricaConsumoRepository metricaRepository;

    @Autowired
    private AlertaDesperdicioRepository alertaRepository;

    public MetricaConsumo salvarMetrica(MetricaConsumo metrica) {
        MetricaConsumo metricaSalva = metricaRepository.save(metrica);

        if (metricaSalva.getUsoCpuPercentual() < 5.0) {
            AlertaDesperdicio novoAlerta = new AlertaDesperdicio();
            novoAlerta.setServidorId(metricaSalva.getServidorId());
            novoAlerta.setStatus("PENDENTE");
            novoAlerta.setCustoEstimadoDesperdicado(50.00);
            
            alertaRepository.save(novoAlerta);
        }

        return metricaSalva;
    }
}