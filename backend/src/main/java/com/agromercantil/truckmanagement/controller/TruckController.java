package com.agromercantil.truckmanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.agromercantil.truckmanagement.dto.TruckRequestDTO;
import com.agromercantil.truckmanagement.model.Truck;
import com.agromercantil.truckmanagement.service.TruckService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/trucks")
@Tag(name = "Caminhões", description = "API para gerenciamento de caminhões")
public class TruckController {

    @Autowired
    private TruckService truckService;

    @GetMapping
    @Operation(summary = "Lista todos os caminhões cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caminhões listados com sucesso")
    })
    public ResponseEntity<List<Truck>> findAll() {
        return ResponseEntity.ok(truckService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um caminhão pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caminhão encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Caminhão não encontrado")
    })
    public ResponseEntity<Truck> findById(@PathVariable Long id) {
        Truck truck = truckService.findById(id);
        return ResponseEntity.ok(truck);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um novo caminhão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Caminhão cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Já existe um caminhão com a mesma placa")
    })
    public Truck save(@Valid @RequestBody TruckRequestDTO request) {
        return truckService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um caminhão existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caminhão atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Caminhão não encontrado"),
            @ApiResponse(responseCode = "409", description = "Já existe outro caminhão com a mesma placa")
    })
    public Truck update(@PathVariable Long id, @Valid @RequestBody TruckRequestDTO request) {
        return truckService.update(id, request);
    }
}
