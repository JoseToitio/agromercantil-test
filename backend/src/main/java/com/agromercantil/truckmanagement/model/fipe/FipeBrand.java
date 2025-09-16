package com.agromercantil.truckmanagement.model.fipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa uma marca de veículo na API FIPE
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FipeBrand {
    private String code;
    private String name;
}