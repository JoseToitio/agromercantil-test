package com.agromercantil.truckmanagement.model.fipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa um ano de fabricação na API FIPE
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FipeYear {
    private String code;
    private String name;
}