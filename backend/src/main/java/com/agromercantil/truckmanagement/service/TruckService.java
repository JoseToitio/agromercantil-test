package com.agromercantil.truckmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agromercantil.truckmanagement.dto.TruckRequestDTO;
import com.agromercantil.truckmanagement.exception.BusinessException;
import com.agromercantil.truckmanagement.model.Truck;
import com.agromercantil.truckmanagement.model.fipe.FipeInfo;
import com.agromercantil.truckmanagement.repository.TruckRepository;

@Service
public class TruckService {

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private FipeService fipeService;

    public List<Truck> findAll() {
        return truckRepository.findAll();
    }

    public Truck findById(Long id) {
        return truckRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Caminhão não encontrado"));
    }

    private Double parsePrice(String priceStr) {
        if (priceStr == null || priceStr.isEmpty())
            return null;
        try {
            String cleaned = priceStr.replace("R$", "").replace(".", "").replace(",", ".").trim();
            return Double.parseDouble(cleaned);
        } catch (Exception e) {
            throw new BusinessException("Erro ao converter preço FIPE: " + priceStr);
        }
    }

    public Truck save(TruckRequestDTO request) {
        if (truckRepository.existsByLicensePlate(request.getLicensePlate())) {
            throw new BusinessException("Já existe um caminhão cadastrado com esta placa");
        }

        // Chama a FIPE com os códigos recebidos
        FipeInfo fipeInfoObj = fipeService.getFipeInfo(
                request.getBrand(),
                request.getModel(),
                request.getManufacturingYear());

        Double price = parsePrice(fipeInfoObj.getPrice());
        // Monta o Truck real, já formatado
        Truck truck = new Truck();
        truck.setLicensePlate(request.getLicensePlate());
        truck.setBrand(fipeInfoObj.getBrand());
        truck.setModel(fipeInfoObj.getModel());
        truck.setManufacturingYear(fipeInfoObj.getModelYear());
        truck.setFipePrice(price);

        return truckRepository.save(truck);
    }

    public Truck update(Long id, TruckRequestDTO request) {
        Truck existingTruck = findById(id);

        if (!existingTruck.getLicensePlate().equals(request.getLicensePlate())
                && truckRepository.existsByLicensePlate(request.getLicensePlate())) {
            throw new BusinessException("Já existe outro caminhão cadastrado com esta placa");
        }

        // Consulta FIPE novamente com os novos códigos
        FipeInfo fipeInfoObj = fipeService.getFipeInfo(
                request.getBrand(),
                request.getModel(),
                request.getManufacturingYear());

        // Converte o preço da FIPE
        Double price = parsePrice(fipeInfoObj.getPrice());

        // Atualiza os dados
        existingTruck.setLicensePlate(request.getLicensePlate());
        existingTruck.setBrand(fipeInfoObj.getBrand());
        existingTruck.setModel(fipeInfoObj.getModel());
        existingTruck.setManufacturingYear(fipeInfoObj.getModelYear());
        existingTruck.setFipePrice(price);

        return truckRepository.save(existingTruck);
    }
}
