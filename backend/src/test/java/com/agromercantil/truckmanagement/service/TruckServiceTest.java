package com.agromercantil.truckmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agromercantil.truckmanagement.dto.TruckRequestDTO;
import com.agromercantil.truckmanagement.exception.BusinessException;

import com.agromercantil.truckmanagement.model.Truck;
import com.agromercantil.truckmanagement.model.fipe.FipeInfo;
import com.agromercantil.truckmanagement.repository.TruckRepository;

@ExtendWith(MockitoExtension.class)
public class TruckServiceTest {

    @Mock
    private TruckRepository truckRepository;

    @Mock
    private FipeService fipeService;

    @InjectMocks
    private TruckService truckService;

    private Truck truck;
    private TruckRequestDTO truckRequest;

    @BeforeEach
    public void setup() {
        truck = new Truck();
        truck.setId(1L);
        truck.setLicensePlate("ABC1234");
        truck.setBrand("Scania");
        truck.setModel("R 450");
        truck.setManufacturingYear(2020);
        truck.setFipePrice(450000.0);

        truckRequest = new TruckRequestDTO();
        truckRequest.setLicensePlate("ABC1234");
        truckRequest.setBrand("Scania");
        truckRequest.setModel("R 450");
        truckRequest.setManufacturingYear("2020");
    }

    @Test
    public void testFindAll() {
        when(truckRepository.findAll()).thenReturn(Arrays.asList(truck));

        List<Truck> result = truckService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(truck.getLicensePlate(), result.get(0).getLicensePlate());
    }

    @Test
    public void testFindById_Success() {
        when(truckRepository.findById(anyLong())).thenReturn(Optional.of(truck));

        Truck result = truckService.findById(1L);

        assertNotNull(result);
        assertEquals(truck.getId(), result.getId());
        assertEquals(truck.getLicensePlate(), result.getLicensePlate());
    }

    @Test
    public void testFindById_NotFound() {
        when(truckRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> truckService.findById(1L));
    }

    @Test
    public void testSave_Success() {
        when(truckRepository.existsByLicensePlate(anyString())).thenReturn(false);

        FipeInfo fipeInfo = new FipeInfo();
        fipeInfo.setPrice("450000.0");
        fipeInfo.setBrand("Scania");
        fipeInfo.setModel("R 450");
        fipeInfo.setModelYear(2020);

        when(fipeService.getFipeInfo(anyString(), anyString(), anyString())).thenReturn(fipeInfo);
        when(truckRepository.save(any(Truck.class))).thenReturn(truck);

        Truck result = truckService.save(truckRequest);

        assertNotNull(result);
        assertEquals(truck.getLicensePlate(), result.getLicensePlate());
        assertEquals(450000.0, result.getFipePrice());
        verify(truckRepository, times(1)).save(any(Truck.class));
    }

    @Test
    public void testSave_DuplicateLicensePlate() {
        when(truckRepository.existsByLicensePlate(anyString())).thenReturn(true);

        assertThrows(BusinessException.class, () -> truckService.save(truckRequest));
        verify(truckRepository, never()).save(any(Truck.class));
    }

    @Test
    public void testUpdate_Success() {
        Truck updatedTruck = new Truck();
        updatedTruck.setId(1L);
        updatedTruck.setLicensePlate("ABC1234");
        updatedTruck.setBrand("Volvo");
        updatedTruck.setModel("FH 540");
        updatedTruck.setManufacturingYear(2021);

        TruckRequestDTO updateRequest = new TruckRequestDTO();
        updateRequest.setLicensePlate("ABC1234");
        updateRequest.setBrand("Volvo");
        updateRequest.setModel("FH 540");
        updateRequest.setManufacturingYear("2021");

        when(truckRepository.findById(anyLong())).thenReturn(Optional.of(truck));

        FipeInfo fipeInfo = new FipeInfo();
        fipeInfo.setPrice("500000.0");
        fipeInfo.setBrand("Volvo");
        fipeInfo.setModel("FH 540");
        fipeInfo.setModelYear(2021);

        when(fipeService.getFipeInfo(anyString(), anyString(), anyString())).thenReturn(fipeInfo);
        when(truckRepository.save(any(Truck.class))).thenReturn(updatedTruck);

        Truck result = truckService.update(1L, updateRequest);

        assertNotNull(result);
        assertEquals(updatedTruck.getBrand(), result.getBrand());
        assertEquals(updatedTruck.getModel(), result.getModel());
        assertEquals(updatedTruck.getManufacturingYear(), result.getManufacturingYear());
        verify(truckRepository, times(1)).save(any(Truck.class));
    }

    @Test
    public void testUpdate_NotFound() {
        when(truckRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> truckService.update(1L, truckRequest));
        verify(truckRepository, never()).save(any(Truck.class));
    }

    @Test
    public void testUpdate_DuplicateLicensePlate() {
        Truck existingTruck = new Truck();
        existingTruck.setId(1L);
        existingTruck.setLicensePlate("XYZ5678");

        TruckRequestDTO updateRequest = new TruckRequestDTO();
        updateRequest.setLicensePlate("ABC1234");

        when(truckRepository.findById(anyLong())).thenReturn(Optional.of(existingTruck));
        when(truckRepository.existsByLicensePlate("ABC1234")).thenReturn(true);

        assertThrows(BusinessException.class, () -> truckService.update(1L, updateRequest));
        verify(truckRepository, never()).save(any(Truck.class));
    }
}
