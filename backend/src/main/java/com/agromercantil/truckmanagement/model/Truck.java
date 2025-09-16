package com.agromercantil.truckmanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trucks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A placa é obrigatória")
    @Pattern(regexp = "[A-Z]{3}[0-9][0-9A-Z][0-9]{2}", message = "Formato de placa inválido")
    @Column(unique = true)
    private String licensePlate;

    @NotBlank(message = "A marca é obrigatória") // Nome da marca (ex: Volvo)
    private String brand;

    @NotBlank(message = "O modelo é obrigatório") // Nome do modelo (ex: FH 540)
    private String model;

    @NotNull(message = "O ano de fabricação é obrigatório")
    private Integer manufacturingYear;

    private Double fipePrice;
}