package com.aep.grinops.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aep.grinops.model.MetricaConsumo;

@Repository 
public interface MetricaConsumoRepository extends MongoRepository<MetricaConsumo, String> {
    
    // Busca todas as métricas de um servidor específico
    List<MetricaConsumo> findByServidorId(String servidorId);
}
