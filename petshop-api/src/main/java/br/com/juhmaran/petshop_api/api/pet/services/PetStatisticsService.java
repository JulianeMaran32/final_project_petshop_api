package br.com.juhmaran.petshop_api.api.pet.services;

import br.com.juhmaran.petshop_api.api.pet.dtos.PetStatisticsResponse;

import java.util.List;

public interface PetStatisticsService {

    List<PetStatisticsResponse> getPetStatistics();

}
