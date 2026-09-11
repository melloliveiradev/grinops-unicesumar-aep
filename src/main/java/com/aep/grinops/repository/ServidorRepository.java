package com.aep.grinops.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aep.grinops.model.Servidor;

@Repository 
public interface ServidorRepository extends MongoRepository<Servidor, String> {

    // Método para buscar servidores por status de ociosidade
    List<Servidor> findByOcioso(boolean ocioso);
    
    List<Servidor> findByProvedorDaNuvem(String provedorDaNuvem);
}