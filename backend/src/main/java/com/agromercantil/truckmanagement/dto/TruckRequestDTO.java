package com.agromercantil.truckmanagement.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TruckRequestDTO {
    @NotBlank(message = "A placa é obrigatória")
    private String licensePlate;

    @NotBlank(message = "O código da marca é obrigatório")
    private String brand; // aqui vem o code da FIPE, ex: "102"

    @NotBlank(message = "O código do modelo é obrigatório")
    private String model; // code da FIPE, ex: "5986"

    @NotBlank(message = "O código do ano é obrigatório")
    private String manufacturingYear; // code da FIPE, ex: "2022-3"
}