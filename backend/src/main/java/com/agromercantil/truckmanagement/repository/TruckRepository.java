package com.agromercantil.truckmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agromercantil.truckmanagement.model.Truck;

import java.util.Optional;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Long> {
    
    /**
     * Verifica se existe um caminhão com a placa informada
     * @param licensePlate Placa do caminhão
     * @return true se existir, false caso contrário
     */
    boolean existsByLicensePlate(String licensePlate);
    
    /**
     * Verifica se existe um caminhão com a placa informada e ID diferente do informado
     * @param licensePlate Placa do caminhão
     * @param id ID do caminhão
     * @return true se existir, false caso contrário
     */
    boolean existsByLicensePlateAndIdNot(String licensePlate, Long id);
    
    /**
     * Busca um caminhão pela placa
     * @param licensePlate Placa do caminhão
     * @return Optional contendo o caminhão, se encontrado
     */
    Optional<Truck> findByLicensePlate(String licensePlate);
}