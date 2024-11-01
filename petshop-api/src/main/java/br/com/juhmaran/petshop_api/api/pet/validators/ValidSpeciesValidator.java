package br.com.juhmaran.petshop_api.api.pet.validators;

import br.com.juhmaran.petshop_api.api.common.enums.Species;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Valida se a espécie do animal é válida.
 *
 * @author Juliane Maran
 */
public class ValidSpeciesValidator implements ConstraintValidator<ValidSpecies, Species> {

    /**
     * Valida se a espécie do animal é válida.
     *
     * @param species espécie do animal
     * @param context contexto de validação
     * @return true se a espécie do animal é válida, false caso contrário
     */
    @Override
    public boolean isValid(Species species, ConstraintValidatorContext context) {
        return (species == Species.DOG || species == Species.CAT || species == Species.BIRD ||
                species == Species.FISH || species == Species.REPTILE || species == Species.OTHER);
    }

}