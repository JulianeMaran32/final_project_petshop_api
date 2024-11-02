package br.com.juhmaran.petshop_api.core.validator;

import br.com.juhmaran.petshop_api.api.common.enums.Species;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopNotFoundException;
import br.com.juhmaran.petshop_api.core.validator.annotations.ValidSpecies;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Validador para espécies.
 * <p>
 * Esta classe implementa a interface {@link ConstraintValidator} para validar se uma espécie é válida.
 * Uma espécie válida deve ser DOG, CAT, BIRD, FISH, REPTILE ou OTHER.
 * </p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 * {@code
 * ValidSpeciesValidator validator = new ValidSpeciesValidator();
 * boolean isValid = validator.isValid(Species.DOG, null);
 * System.out.println("Espécie válida: " + isValid);
 * }
 * </pre>
 *
 * @author Juliane Maran
 * @see ValidSpecies
 * @see ConstraintValidator
 * @see PetShopNotFoundException
 */
@Slf4j
public class ValidSpeciesValidator implements ConstraintValidator<ValidSpecies, Species> {

    @Override
    public boolean isValid(Species species, ConstraintValidatorContext context) {
        boolean isValid = (species == Species.DOG || species == Species.CAT || species == Species.BIRD ||
                species == Species.FISH || species == Species.REPTILE || species == Species.OTHER);

        if (isValid) {
            log.info("Espécie válida: {}", species);
        } else {
            log.warn("Espécie inválida: {}", species);
            throw new PetShopNotFoundException("Espécie não encontrada: " + species);
        }
        return true;
    }

}