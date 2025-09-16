package com.agromercantil.truckmanagement.model.fipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa um modelo de veículo na API FIPE
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FipeModel {
    private String code;
    private String name;
}