package com.agromercantil.truckmanagement.model.fipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa o preço de um veículo na API FIPE
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FipeInfo {
    private String vehicleType;
    private String price; // Ex: "R$ 245.859,00"
    private String brand; // Nome da marca (Scania, Volvo, etc)
    private String model; // Nome do modelo (FH 540, etc)
    private Integer modelYear;
    private String fuel;
    private String codeFipe;
    private String referenceMonth;
    private String fuelAcronym;
}

