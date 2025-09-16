package com.agromercantil.truckmanagement.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.agromercantil.truckmanagement.model.fipe.FipeBrand;
import com.agromercantil.truckmanagement.model.fipe.FipeModel;
import com.agromercantil.truckmanagement.model.fipe.FipeInfo;
import com.agromercantil.truckmanagement.model.fipe.FipeYear;

@Service
public class FipeService {

        private static final String FIPE_API_URL = "https://fipe.parallelum.com.br/api/v2";
        private static final String VEHICLE_TYPE = "trucks";

        @Autowired
        private WebClient.Builder webClientBuilder;

        public List<FipeBrand> getBrands() {
                String url = String.format("%s/%s/brands", FIPE_API_URL, VEHICLE_TYPE);
                return webClientBuilder.build()
                                .get()
                                .uri(url)
                                .retrieve()
                                .bodyToMono(FipeBrand[].class)
                                .map(Arrays::asList)
                                .block();
        }

        public List<FipeModel> getModels(String brandCode) {
                String url = String.format("%s/%s/brands/%s/models", FIPE_API_URL, VEHICLE_TYPE, brandCode);
                return webClientBuilder.build()
                                .get()
                                .uri(url)
                                .retrieve()
                                .bodyToMono(FipeModel[].class)
                                .map(Arrays::asList)
                                .block();
        }

        public List<FipeYear> getYears(String brandCode, String modelCode) {
                String url = String.format("%s/%s/brands/%s/models/%s/years", FIPE_API_URL, VEHICLE_TYPE, brandCode,
                                modelCode);
                return webClientBuilder.build()
                                .get()
                                .uri(url)
                                .retrieve()
                                .bodyToMono(FipeYear[].class)
                                .map(Arrays::asList)
                                .block();
        }

        public FipeInfo getFipeInfo(String brandCode, String modelCode, String yearCode) {
                String url = String.format(
                                "%s/%s/brands/%s/models/%s/years/%s",
                                FIPE_API_URL, VEHICLE_TYPE, brandCode, modelCode, yearCode);

                return webClientBuilder.build()
                                .get()
                                .uri(url)
                                .retrieve()
                                .bodyToMono(FipeInfo.class)
                                .block();
        }
}
