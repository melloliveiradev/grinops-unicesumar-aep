package com.aep.grinops.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aep.grinops.model.AlertaDesperdicio;

@Repository
public interface AlertaDesperdicioRepository extends MongoRepository<AlertaDesperdicio, String> {
    
    List<AlertaDesperdicio> findByServidorId(String servidorId);
    
    List<AlertaDesperdicio> findByStatus(String status);

    List<AlertaDesperdicio> findByServidorIdAndStatus(String servidorId, String status);
}