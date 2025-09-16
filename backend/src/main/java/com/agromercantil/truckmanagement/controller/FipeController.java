package com.agromercantil.truckmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agromercantil.truckmanagement.model.fipe.FipeBrand;
import com.agromercantil.truckmanagement.model.fipe.FipeModel;
import com.agromercantil.truckmanagement.model.fipe.FipeYear;
import com.agromercantil.truckmanagement.model.fipe.FipeInfo;
import com.agromercantil.truckmanagement.service.FipeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/fipe")
@Tag(name = "FIPE", description = "API para integração com a tabela FIPE")
public class FipeController {

    @Autowired
    private FipeService fipeService;

    @GetMapping("/brands")
    public ResponseEntity<List<FipeBrand>> getBrands() {
        return ResponseEntity.ok(fipeService.getBrands());
    }

    @GetMapping("/brands/{brandCode}/models")
    public ResponseEntity<List<FipeModel>> getModels(@PathVariable String brandCode) {
        return ResponseEntity.ok(fipeService.getModels(brandCode));
    }

    @GetMapping("/brands/{brandCode}/models/{modelCode}/years")
    public ResponseEntity<List<FipeYear>> getYears(
            @PathVariable String brandCode,
            @PathVariable String modelCode) {
        return ResponseEntity.ok(fipeService.getYears(brandCode, modelCode));
    }

    @GetMapping("/validate")
    @Operation(summary = "Busca o preço de um caminhão diretamente pelos códigos FIPE")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Preço encontrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro ao consultar a API FIPE")
    })
    public ResponseEntity<FipeInfo> validateAndGetPrice(
            @RequestParam String brand,
            @RequestParam String model,
            @RequestParam String year) {
        try {
            FipeInfo price = fipeService.getFipeInfo(brand, model, year);
            return ResponseEntity.ok(price);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
