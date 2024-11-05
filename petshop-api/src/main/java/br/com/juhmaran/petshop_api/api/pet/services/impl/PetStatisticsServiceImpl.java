package br.com.juhmaran.petshop_api.api.pet.services.impl;

import br.com.juhmaran.petshop_api.api.pet.dtos.PetStatisticsResponse;
import br.com.juhmaran.petshop_api.api.pet.repositories.PetRepository;
import br.com.juhmaran.petshop_api.api.pet.services.PetStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetStatisticsServiceImpl implements PetStatisticsService {

    private final PetRepository petRepository;

    @Transactional
    @Override
    public List<PetStatisticsResponse> getPetStatistics() {
        List<Object[]> results = petRepository.getPetStatistics();
        return results.stream()
                .map(result -> new PetStatisticsResponse((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }


}
