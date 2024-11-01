package br.com.juhmaran.petshop_api.api.pet.utils;

import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopBadRequestException;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.Period;

@UtilityClass
public class PetAgeCalculator {

    public static int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            throw new PetShopBadRequestException("Data de nascimento não pode ser nula");
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

}
